(ns frontend.components.property
  "Block properties management."
  (:require [clojure.set :as set]
            [clojure.string :as string]
            [frontend.components.property.value :as pv]
            [frontend.components.select :as select]
            [frontend.config :as config]
            [frontend.db :as db]
            [frontend.db-mixins :as db-mixins]
            [frontend.db.model :as db-model]
            [frontend.handler.db-based.property :as db-property-handler]
            [frontend.handler.notification :as notification]
            [frontend.handler.property :as property-handler]
            [frontend.handler.reorder :as reorder-handler]
            [frontend.handler.property.util :as pu]
            [frontend.mixins :as mixins]
            [frontend.modules.shortcut.core :as shortcut]
            [frontend.search :as search]
            [frontend.state :as state]
            [frontend.ui :as ui]
            [frontend.util :as util]
            [logseq.db.property :as db-property]
            [rum.core :as rum]
            [frontend.handler.route :as route-handler]
            ))

(defn- update-property!
  [property property-name property-schema]
  (property-handler/update-property!
   (state/get-current-repo)
   (:block/uuid property)
   {:property-name property-name
    :property-schema property-schema}))

(rum/defc icon
  [block {:keys [_type id]} {:keys [disabled?]}]            ; only :emoji supported yet
  (let [repo (state/get-current-repo)
        icon-property-id (:block/uuid (db/entity [:block/name "icon"]))]
    (ui/dropdown
     (fn [{:keys [toggle-fn]}]
       (if id
         [:a {:on-click #(when-not disabled? (toggle-fn))}
          [:em-emoji {:id id}]]
         [:a.flex.flex-row.items-center {:on-click #(when-not disabled? (toggle-fn))}
          (ui/icon "point" {:size 16})
          [:div.ml-1.text-sm "Pick another icon"]]))
     (fn [{:keys [toggle-fn]}]
       (ui/emoji-picker
        {:auto-focus true
         :on-emoji-select (fn [icon]
                            (when-let [id (.-id icon)]
                              (property-handler/update-property! repo (:block/uuid block) {:properties {icon-property-id {:type :emoji
                                                                                                                          :id id}}}))
                            (toggle-fn))})))))

(rum/defcs class-select < (rum/local false ::open?)
  [state *property-schema schema-classes {:keys [multiple-choices? disabled?]
                                          :or {multiple-choices? true}}]
  (let [*open? (::open? state)]
    (if @*open?
      (let [classes (db-model/get-all-classes (state/get-current-repo))
            options (map (fn [[name id]] {:label name
                                          :value id})
                         classes)
            opts (cond->
                  {:items options
                   :input-default-placeholder (if multiple-choices? "Choose classes" "Choose class")
                   :dropdown? true
                   :close-modal? false
                   :multiple-choices? multiple-choices?
                   :selected-choices schema-classes
                   :extract-fn :label
                   :extract-chosen-fn :value
                   :input-opts {:on-blur (fn [] (reset! *open? false))
                                :on-key-down
                                (fn [e]
                                  (case (util/ekey e)
                                    "Escape"
                                    (do
                                      (util/stop e)
                                      (reset! *open? false))
                                    nil))}}
                   multiple-choices?
                   (assoc :on-apply (fn [choices]
                                      (swap! *property-schema assoc :classes choices)
                                      (reset! *open? false)))

                   (not multiple-choices?)
                   (assoc :on-chosen (fn [value]
                                       (swap! *property-schema assoc :classes [value])
                                       (reset! *open? false))))]
        (select/select opts))
      [:div.flex.flex-1.flex-row.cursor.items-center.flex-wrap.gap-2.col-span-3
       {:on-click #(when-not disabled? (reset! *open? true))}
       (if (seq schema-classes)
         (for [class schema-classes]
           (when-let [page (db/entity [:block/uuid class])]
             (let [page-name (:block/original-name page)]
               [:a.text-sm (str "#" page-name)])))
         [:div.text-sm
          (if multiple-choices?
            "Click to add classes"
            "Click to select a class")])])))

(rum/defcs enum-item-config <
  {:init (fn [state]
           (let [{:keys [name description]} (first (:rum/args state))]
             (assoc state
                    ::name (atom (or name ""))
                    ::description (atom (or description "")))))}
  [state {:keys [id name description]} {:keys [toggle-fn on-save]}]
  (let [*name (::name state)
        *description (::description state)]
    [:div.flex.flex-col.gap-4.p-4.whitespace-nowrap.w-96
     [:div.grid.grid-cols-5.gap-1.items-center.leading-8
      [:label.col-span-2 "Name:"]
      [:input.form-input.col-span-3
       {:default-value @*name
        :on-change #(reset! *name (util/evalue %))}]]
     [:div.grid.grid-cols-5.gap-1.items-start.leading-8
      [:label.col-span-2 "Description:"]
      [:div.col-span-3
       (ui/ls-textarea
        {:on-change #(reset! *description (util/evalue %))
         :value @*description})]]
     [:div
      (ui/button
       "Save"
       :on-click (fn [e]
                   (util/stop e)
                   (when-not (string/blank? @*name)
                     (let [result (when on-save (on-save (string/trim @*name) @*description))]
                       (if (= :value-exists result)
                         (notification/show! (str "Choice already exist") :warning)
                         (when toggle-fn (toggle-fn)))))))]]))

(rum/defcs enum-new-item <
  (rum/local "" ::name)
  (rum/local "" ::description)
  [state {:keys [toggle-fn on-save]}]
  (let [*name (::name state)
        *description (::description state)]
    [:div.flex.flex-col.gap-4.p-4.whitespace-nowrap.w-96
     [:div.grid.grid-cols-5.gap-1.items-center.leading-8
      [:label.col-span-2 "Name:"]
      [:input.form-input.col-span-3
       {:default-value ""
        :on-change #(reset! *name (util/evalue %))}]]
     [:div.grid.grid-cols-5.gap-1.items-start.leading-8
      [:label.col-span-2 "Description:"]
      [:div.col-span-3
       (ui/ls-textarea
        {:on-change #(reset! *description (util/evalue %))
         :value @*description})]]
     [:div
      (ui/button
       "Save"
       :on-click (fn [e]
                   (util/stop e)
                   (when-not (string/blank? @*name)
                     (let [result (when on-save (on-save (string/trim @*name) @*description))]
                       (if (= :value-exists result)
                         (notification/show! (str "Choice already exist") :warning)
                         (when toggle-fn (toggle-fn)))))))]]))

(rum/defcs choice-with-close <
  (rum/local false ::hover?)
  [state name {:keys [toggle-fn delete-choice]}]
  (let [*hover? (::hover? state)]
    [:div.flex.flex-1.flex-row.items-center.gap-2.justify-between
     {:on-mouse-over #(reset! *hover? true)
      :on-mouse-out #(reset! *hover? false)}
     [:a {:on-click toggle-fn}
      name]
     (when @*hover?
       [:a.fade-link.flex {:on-click delete-choice
                           :title "Delete this choice"}
        (ui/icon "X")])]))

(rum/defcs choice-item <
  (rum/local nil ::up?)
  (rum/local nil ::dragging-over)
  [state property item values order *property-schema *property-name dropdown-opts]
  (let [up? (get state ::up?)
        dragging-over (get state ::dragging-over)
        {:keys [id name]} item]
    [:li
     {:key name
      :title name
      :data-ref name
      :draggable true
      :on-drag-start (fn [event]
                       (.setData (.-dataTransfer event) "id" (str id)))
      :on-drag-over (fn [e]
                      (util/stop e)
                      (reset! dragging-over id)
                      (when-not (= (str id) (.getData (.-dataTransfer e) "id"))
                        (reset! up? (util/move-up? e))))
      :on-drag-leave (fn [_e]
                       (reset! dragging-over nil))
      :on-drop (fn [e]
                 (when-let [target (some-> (.getData (.-dataTransfer e) "id") uuid)]
                   (let [up? (util/move-up? e)
                         new-order (reorder-handler/reorder-items order
                                                                  {:target target
                                                                   :to id
                                                                   :up? up?})]
                     (when (seq new-order)
                       (swap! *property-schema assoc :enum-config {:values values
                                                                 :order new-order})
                       (update-property! property @*property-name @*property-schema))))
                 (reset! up? nil)
                 (reset! dragging-over nil))}
     (ui/dropdown
      (fn [opts]
        (choice-with-close
         name
         (assoc opts :delete-choice
                (fn []
                  (let [new-values (dissoc values id)
                        new-order (vec (remove #{id} order))]
                    (swap! *property-schema assoc :enum-config {:values new-values
                                                                :order new-order})
                        ;; FIXME: how to handle block properties with this value?
                        ;; 1. delete the blocks' property that has this value
                        ;; 2. update exist values to the default value if exists
                        ;; 3. soft delete, users can still see it in some existing blocks,
                        ;;    but they will not see it when adding or updating this property
                    (update-property! property @*property-name @*property-schema))))))
      (fn [opts]
        (enum-item-config
         item
         (assoc opts :on-save
                (fn [name description]
                  (if (some (fn [[vid m]] (and (not= vid id) (= name (:name m)))) values)
                    :value-exists
                    (let [new-values (assoc values id {:name name
                                                       :description description})]
                      (swap! *property-schema assoc :enum-config {:values new-values
                                                                  :order order})
                      (update-property! property @*property-name @*property-schema)))))))
      dropdown-opts)]))

(rum/defc enum-choices
  [property *property-name *property-schema {:keys [values order] :as _config}]
  (let [dropdown-opts {:modal-class (util/hiccup->class
                                     "origin-top-right.absolute.left-0.rounded-md.shadow-lg")}
        order (if (not= (count order) (count values))
                (vec (concat order (remove (set order) (keys values))))
                order)]
    [:div.enum-choices.flex.flex-col
     [:ol
      (for [id order]
        (let [item (assoc (get values id) :id id)]
          (choice-item property item values order *property-schema *property-name dropdown-opts)))]
     (ui/dropdown
      (fn [{:keys [toggle-fn]}]
        [:a.fade-link.flex.flex-row.items-center.gap-1.leading-8 {:on-click toggle-fn}
         (ui/icon "plus" {:size 16})
         "Add choice"])
      (fn [opts]
        (enum-new-item (assoc opts :on-save
                              (fn [name description]
                                (if (contains? (set (map :name (vals values))) name)
                                  :value-exists
                                  (let [id (random-uuid)
                                        new-values (assoc values id {:name name
                                                                     :description description})
                                        new-order (vec (conj order id))]
                                    (swap! *property-schema assoc :enum-config {:values new-values
                                                                                :order new-order})
                                    (update-property! property @*property-name @*property-schema)))))))
      dropdown-opts)]))

(rum/defcs property-config <
  rum/reactive
  (rum/local nil ::property-name)
  (rum/local nil ::property-schema)
  {:will-mount (fn [state]
                 (let [[property] (:rum/args state)]
                   (reset! (::property-name state) (:block/original-name property))
                   (reset! (::property-schema state) (:block/schema property))
                   state))}
  [state property {:keys [toggle-fn inline-text] :as opts}]
  (let [*property-name (::property-name state)
        *property-schema (::property-schema state)
        built-in-property? (contains? db-property/built-in-properties-keys-str (:block/original-name property))
        property (db/sub-block (:db/id property))
        disabled? (or built-in-property? config/publishing?)]
    [:div.property-configure.flex.flex-1.flex-col
     [:div.font-bold.text-xl
      (if disabled?
        "Property fields"
        "Configure property")]

     [:div.grid.gap-2.p-1.mt-4
      [:div.grid.grid-cols-4.gap-1.items-center.leading-8
       [:label.col-span-1 "Name:"]
       [:input.form-input.col-span-2
        {:on-change #(reset! *property-name (util/evalue %))
         :disabled disabled?
         :value @*property-name}]]

      [:div.grid.grid-cols-4.gap-1.items-center.leading-8
       [:label.col-span-1 "Icon:"]
       (let [icon-value (pu/get-property property :icon)]
         [:div.col-span-3
          (icon property icon-value {:disabled? disabled?})])]

      [:div.grid.grid-cols-4.gap-1.items-center.leading-8
       [:label.col-span-1 "Schema type:"]
       (let [schema-types (->> (concat property-handler/user-face-builtin-schema-types
                                       (when built-in-property?
                                         property-handler/internal-builtin-schema-types))
                               (map (comp string/capitalize name))
                               (map (fn [type]
                                      {:label (if (= type "Default") "Text" type)
                                       :disabled disabled?
                                       :value type
                                       :selected (= (keyword (string/lower-case type))
                                                    (:type @*property-schema))})))]
         [:div.col-span-2
          (ui/select schema-types
                     (fn [_e v]
                       (let [type (keyword (string/lower-case v))]
                         (swap! *property-schema assoc :type type))))])]

      (case (:type @*property-schema)
        :page
        [:div.grid.grid-cols-4.gap-1.items-center.leading-8
         [:label "Specify classes:"]
         (class-select *property-schema (:classes @*property-schema) (assoc opts :disabled? disabled?))]

        :template
        [:div.grid.grid-cols-4.gap-1.items-center.leading-8
         [:label "Specify template:"]
         (class-select *property-schema (:classes @*property-schema)
                       (assoc opts :multiple-choices? false :disabled? disabled?))]

        :enum
        [:div.grid.grid-cols-4.gap-1.items-start.leading-8
         [:label.col-span-1 "Enum choices:"]
         [:div.col-span-3
          (enum-choices property *property-name *property-schema (:enum-config @*property-schema))]]

        nil)

      (when (= :enum (:type @*property-schema))
        (let [position (:position @*property-schema)
              choices (map
                       (fn [item]
                         (assoc item :selected
                                (or (and position (= (:value item) position))
                                    (and (nil? position) (= (:value item) "properties")))))
                       [{:label "Block properties"
                         :value "properties"}
                        {:label "Beginning of the block"
                         :value "block-beginning"}
                        {:label "Ending of the block"
                         :value "block-ending"}])]
          [:div.grid.grid-cols-4.gap-1.items-start.leading-8
           [:label.col-span-1 "UI position:"]
           [:div.col-span-3
            (ui/select choices
              (fn [_e v]
                (swap! *property-schema assoc :position v)))]]))

      (when-not (contains? #{:checkbox :default :template :enum} (:type @*property-schema))
        [:div.grid.grid-cols-4.gap-1.items-center.leading-8
         [:label "Multiple values:"]
         (let [many? (boolean (= :many (:cardinality @*property-schema)))]
           (ui/checkbox {:checked many?
                         :disabled disabled?
                         :on-change (fn []
                                      (swap! *property-schema assoc :cardinality (if many? :one :many)))}))])

      (let [hide? (:hide? @*property-schema)]
        [:div.grid.grid-cols-4.gap-1.items-center.leading-8
         [:label "Hide by default:"]
         (ui/checkbox {:checked hide?
                       :disabled disabled?
                       :on-change (fn []
                                    (swap! *property-schema assoc :hide? (not hide?)))})])

      [:div.grid.grid-cols-4.gap-1.items-start.leading-8
       [:label "Description:"]
       [:div.col-span-3
        (if (and disabled? inline-text)
          (inline-text {} :markdown (:description @*property-schema))
          (ui/ls-textarea
           {:on-change (fn [e]
                         (swap! *property-schema assoc :description (util/evalue e)))
            :disabled disabled?
            :value (:description @*property-schema)}))]]

      [:div
       (when-not disabled?
         (ui/button
          "Save"
          :on-click (fn [e]
                      (util/stop e)
                      (update-property! property @*property-name @*property-schema)
                      (when toggle-fn (toggle-fn)))))]]]))

(defn- get-property-from-db [name]
  (when-not (string/blank? name)
    (db/entity [:block/name (util/page-name-sanity-lc name)])))

(defn- add-property-from-dropdown
  "Adds an existing or new property from dropdown. Used from a block or page context.
   For pages, used to add both schema properties or properties for a page"
  [entity property-name {:keys [class-schema? blocks-container-id page-configure?
                                *show-new-property-config?]}]
  (let [repo (state/get-current-repo)]
    ;; existing property selected or entered
    (if-let [property (get-property-from-db property-name)]
      (if (contains? db-property/hidden-built-in-properties (keyword property-name))
        (do (notification/show! "This is a built-in property that can't be used." :error)
            (pv/exit-edit-property))
        ;; Both conditions necessary so that a class can add its own page properties
        (if (and (contains? (:block/type entity) "class") class-schema?)
          (pv/add-property! entity property-name "" {:class-schema? class-schema?
                                                  ;; Only enter property names from sub-modal as inputting
                                                  ;; property values is buggy in sub-modal
                                                     :exit-edit? page-configure?})
          (let [editor-id (str "ls-property-" blocks-container-id (:db/id entity) "-" (:db/id property))]
            (pv/set-editing! property editor-id "" ""))))
      ;; new property entered
      (if (db-property/valid-property-name? property-name)
        (if (contains? (:block/type entity) "class")
          (pv/add-property! entity property-name "" {:class-schema? class-schema? :exit-edit? page-configure?})
          (do
            (db-property-handler/upsert-property! repo property-name {:type :default} {})
            (when *show-new-property-config?
              (reset! *show-new-property-config? true))))
        (do (notification/show! "This is an invalid property name. A property name cannot start with page reference characters '#' or '[['." :error)
            (pv/exit-edit-property))))))

(rum/defcs property-input < rum/reactive
  (rum/local false ::show-new-property-config?)
  shortcut/disable-all-shortcuts
  [state entity *property-key *property-value {:keys [class-schema? page-configure? in-block-container?]
                                               :as opts}]
  (let [*show-new-property-config? (::show-new-property-config? state)
        entity-properties (->> (keys (:block/properties entity))
                               (map #(:block/original-name (db/entity [:block/uuid %])))
                               (set))
        existing-tag-alias (reduce (fn [acc prop]
                                     (if (seq (get entity (get-in db-property/built-in-properties [prop :attribute])))
                                       (conj acc (get-in db-property/built-in-properties [prop :original-name]))
                                       acc))
                                   #{}
                                   [:tags :alias])
        exclude-properties* (set/union entity-properties existing-tag-alias)
        exclude-properties (set/union exclude-properties* (set (map string/lower-case exclude-properties*)))
        properties (->> (search/get-all-properties)
                        (remove exclude-properties))]
    [:div.ls-property-input.flex.flex-1.flex-row.items-center.flex-wrap.gap-1
     (if in-block-container? {:style {:padding-left 22}} {})
     (if @*property-key
       (when-let [property (get-property-from-db @*property-key)]
         [:div.ls-property-add.grid.grid-cols-5.gap-1.flex.flex-1.flex-row.items-center
          [:div.flex.flex-row.items-center.col-span-2
           [:span.bullet-container.cursor [:span.bullet]]
           [:div {:style {:padding-left 6}} @*property-key]]
          [:div.col-span-3.flex.flex-row {:on-mouse-down (fn [e] (util/stop-propagation e))}
           (when (not (and class-schema? page-configure?))
             (if @*show-new-property-config?
               (ui/dropdown
                (fn [_opts]
                  (pv/property-value entity property @*property-value (assoc opts :editing? true)))
                (fn [{:keys [toggle-fn]}]
                  [:div.p-6
                   (property-config property (merge opts {:toggle-fn toggle-fn
                                                          :block entity}))])
                {:initial-open? true
                 :modal-class (util/hiccup->class
                               "origin-top-right.absolute.left-0.rounded-md.shadow-lg.mt-2")})
               (pv/property-value entity property @*property-value (assoc opts :editing? true))))]])

       [:div.ls-property-add.flex.flex-row.items-center
        [:span.bullet-container.cursor [:span.bullet]]
        [:div.ls-property-key {:style {:padding-left 6
                                       :height "1.5em"}} ; TODO: ugly
         (select/select {:items (map (fn [x] {:value x}) properties)
                         :dropdown? true
                         :close-modal? false
                         :show-new-when-not-exact-match? true
                         :exact-match-exclude-items exclude-properties
                         :input-default-placeholder "Add a property"
                         :on-chosen (fn [{:keys [value]}]
                                      (reset! *property-key value)
                                      (add-property-from-dropdown entity value (assoc opts :*show-new-property-config? *show-new-property-config?)))
                         :input-opts {:on-blur (fn [] (pv/exit-edit-property))
                                      :on-key-down
                                      (fn [e]
                                        (case (util/ekey e)
                                          "Escape"
                                          (pv/exit-edit-property)
                                          nil))}})]])]))

(rum/defcs new-property < rum/reactive
  (rum/local nil ::property-key)
  (rum/local nil ::property-value)
  (mixins/event-mixin
   (fn [state]
     (mixins/hide-when-esc-or-outside
      state
      :on-hide (fn [] (property-handler/set-editing-new-property! nil))
      :node (js/document.getElementById "edit-new-property"))))
  [state block edit-input-id properties new-property? opts]
  [:div.ls-new-property
   (let [*property-key (::property-key state)
         *property-value (::property-value state)]
     (cond
       new-property?
       [:div#edit-new-property
        (property-input block *property-key *property-value opts)]

       (and (or (:page-configure? opts)
                (seq properties)
                (seq (:block/alias block)))
            (not config/publishing?)
            (or (:page-configure? opts) (not (:in-block-container? opts))))
       ;; When the :hidden-new-property? option is set, adding the
       ;; first property is hidden and only appears when hovered over
       (when (or (not (:hidden-new-property? opts))
                 (seq (:block/properties block))
                 (:hover? opts))
         [:a.fade-link.flex
          {:on-click (fn []
                       (property-handler/set-editing-new-property! edit-input-id)
                       (reset! *property-key nil)
                       (reset! *property-value nil))}
          [:div.flex.flex-row.items-center {:style {:padding-left 1}}
           (ui/icon "circle-plus" {:size 15})
           [:div.ml-1.text-sm "Add property"]]])

       :else
       [:div {:style {:height 28}}]))])

(defn- property-collapsed?
  [block property]
  (boolean?
   (some (fn [p] (= (:db/id property) (:db/id p)))
         (:block/collapsed-properties block))))

(rum/defcs property-key <
  (rum/local false ::hover?)
  [state block property {:keys [class-schema? block? collapsed? inline-text]}]
  (let [*hover? (::hover? state)
        repo (state/get-current-repo)
        icon (pu/get-property property :icon)]
    [:div.flex.flex-row.items-center {:on-mouse-over #(reset! *hover? true)
                                      :on-mouse-leave #(reset! *hover? false)}
     (when block?
       [:a.block-control
        {:on-click (fn [event]
                     (util/stop event)
                     (property-handler/collapse-expand-property! repo block property (not collapsed?)))}
        [:span {:class (cond
                         (or collapsed? @*hover?)
                         "control-show cursor-pointer"
                         :else
                         "control-hide")}
         (ui/rotating-arrow collapsed?)]])
     (ui/dropdown
      (fn [{:keys [toggle-fn]}]
        [:a.flex {:on-click toggle-fn}
         (or
          (when-let [id (:id icon)]
            (when (= :emoji (:type icon))
              [:em-emoji {:id id}]))
          [:span.bullet-container.cursor (when collapsed? {:class "bullet-closed"})
           [:span.bullet]])])
      (fn [{:keys [toggle-fn]}]
        (ui/emoji-picker
         {:auto-focus true
          :on-emoji-select (fn [icon]
                             (when-let [id (.-id icon)]
                               (let [icon-property-id (:block/uuid (db/entity [:block/name "icon"]))]
                                 (property-handler/update-property! repo
                                                                    (:block/uuid property)
                                                                    {:properties {icon-property-id {:type :emoji
                                                                                                    :id id}}})))
                             (toggle-fn))}))
      {:modal-class (util/hiccup->class
                     "origin-top-right.absolute.left-0.rounded-md.shadow-lg.mt-2")})
     (ui/dropdown
      (if config/publishing?
        (fn [_opts]
          [:a.property-k
           {:on-click #(route-handler/redirect-to-page! (:block/name property))}
           [:div {:style {:padding-left 6}} (:block/original-name property)]])
        (fn [{:keys [toggle-fn]}]
          [:a.property-k
           {:data-propertyid (:block/uuid property)
            :data-blockid (:block/uuid block)
            :data-class-schema (boolean class-schema?)
            :title (str "Configure property: " (:block/original-name property))
            :on-mouse-down (fn [e]
                             (when (util/meta-key? e)
                               (route-handler/redirect-to-page! (:block/name property))
                               (.preventDefault e)))
            :on-click toggle-fn}
           [:div {:style {:padding-left 6}} (:block/original-name property)]]))
      (fn [{:keys [toggle-fn]}]
        [:div.p-8
         (property-config property {:toggle-fn toggle-fn :inline-text inline-text})])
      {:modal-class (util/hiccup->class
                     "origin-top-right.absolute.left-0.rounded-md.shadow-lg")})]))

(defn- resolve-linked-block-if-exists
  "Properties will be updated for the linked page instead of the refed block.
  For example, the block below has a reference to the page \"How to solve it\",
  we'd like the properties of the class \"book\" (e.g. Authors, Published year)
  to be assigned for the page `How to solve it` instead of the referenced block.

  Block:
  - [[How to solve it]] #book
  "
  [block]
  (if-let [linked-block (:block/link block)]
    (db/sub-block (:db/id linked-block))
    (db/sub-block (:db/id block))))

(rum/defc properties-section < rum/reactive db-mixins/query
  [block properties {:keys [inline-text] :as opts}]
  (when (seq properties)
    (for [[k v] properties]
      (when (uuid? k)
        (when-let [property (db/sub-block (:db/id (db/entity [:block/uuid k])))]
          (let [type (get-in property [:block/schema :type] :default)
                block? (and (contains? #{:default :template} type)
                            (uuid? v)
                            (db/entity [:block/uuid v]))
                collapsed? (when block? (property-collapsed? block property))]
            [:div {:class (if block?
                            "flex flex-1 flex-col gap-1 property-block"
                            "property-pair items-start")}
             [:div.property-key
              {:class "col-span-2"}
              (property-key block property (assoc (select-keys opts [:class-schema?])
                                                  :block? block?
                                                  :collapsed? collapsed?
                                                  :inline-text inline-text))]
             (if (and (:class-schema? opts) (:page-configure? opts))
               [:div.property-description.text-sm.opacity-70
                {:class "col-span-3"}
                (inline-text {} :markdown (get-in property [:block/schema :description]))]
               (when-not collapsed?
                 [:div.property-value {:class (if block?
                                                "block-property-value"
                                                "col-span-3 inline-grid")}
                  (pv/property-value block property v opts)]))]))))))

(rum/defcs hidden-properties < (rum/local true ::hide?)
  [state block hidden-properties opts]
  (let [*hide? (::hide? state)]
    [:div.hidden-properties.flex.flex-col.gap-1
     [:a.text-sm.flex.flex-row.items-center.fade-link.select-none
      {:on-click #(swap! *hide? not)}
      [:span {:style {:margin-left -1}}
       (ui/rotating-arrow @*hide?)]
      [:div {:style {:margin-left 3}} "Hidden properties"]]
     (when-not @*hide?
       (properties-section block hidden-properties opts))]))



(rum/defcs properties-area < rum/reactive
  (rum/local false ::hover?)
  {:init (fn [state]
           (assoc state ::blocks-container-id (or (:blocks-container-id (last (:rum/args state)))
                                                  (state/next-blocks-container-id))))}
  [state target-block edit-input-id {:keys [in-block-container? page-configure?] :as opts}]
  (let [*hover? (::hover? state)
        block (resolve-linked-block-if-exists target-block)
        class-schema? (and (:class-schema? opts) (:block/schema block))
        block-properties (:block/properties block)
        properties (if (and class-schema? page-configure?)
                     (let [properties (:properties (:block/schema block))]
                       (map (fn [k] [k nil]) properties))
                     (sort-by first block-properties))
        alias (set (map :block/uuid (:block/alias block)))
        alias-properties (when (seq alias)
                           [[(:block/uuid (db/entity [:block/name "alias"])) alias]])
        remove-built-in-properties (fn [properties]
                                     (remove (fn [x]
                                               (let [id (if (uuid? x) x (first x))]
                                                 (when (uuid? id)
                                                   (contains? db-property/hidden-built-in-properties (keyword (:block/name (db/entity [:block/uuid id])))))))
                                             properties))
        {:keys [classes all-classes classes-properties]} (property-handler/get-block-classes-properties (:db/id block))
        one-class? (= 1 (count classes))
        block-own-properties (->> (concat (seq alias-properties)
                                          (seq properties))
                                  remove-built-in-properties
                                  (remove (fn [[id _]] ((set classes-properties) id))))
        ;; This section produces own-properties and full-hidden-properties
        hide-with-property-id (fn [property-id]
                                (let [eid (if (uuid? property-id) [:block/uuid property-id] property-id)]
                                  (boolean (:hide? (:block/schema (db/entity eid))))))
        property-hide-f (cond
                          config/publishing?
                          ;; Publishing is read only so hide all blank properties as they
                          ;; won't be edited and distract from properties that have values
                          (fn [[property-id property-value]]
                            (or (nil? property-value)
                                (hide-with-property-id property-id)))
                          (:ui/hide-empty-properties? (state/get-config))
                          (fn [[property-id property-value]]
                            ;; User's selection takes precedence over config
                            (if (contains? (:block/schema (db/entity [:block/uuid property-id])) :hide?)
                              (hide-with-property-id property-id)
                              (nil? property-value)))
                          :else
                          (comp hide-with-property-id first))
        {block-hidden-properties true
         block-own-properties' false} (group-by property-hide-f block-own-properties)
        {class-hidden-properties true
         class-own-properties false} (group-by property-hide-f
                                               (map (fn [id] [id (get block-properties id)]) classes-properties))
        own-properties (->>
                        (if one-class?
                          (concat block-own-properties' class-own-properties)
                          block-own-properties')
                        (remove (fn [[id _]] (property-handler/enum-other-position? id))))
        full-hidden-properties (concat block-hidden-properties class-hidden-properties)
        new-property? (= edit-input-id (state/sub :ui/new-property-input-id))
        class->properties (loop [classes all-classes
                                 properties #{}
                                 result []]
                            (if-let [class (first classes)]
                              (let [cur-properties (->> (:properties (:block/schema class))
                                                        (remove properties)
                                                        (remove hide-with-property-id)
                                                        (remove property-handler/enum-other-position?))]
                                (recur (rest classes)
                                       (set/union properties (set cur-properties))
                                       (conj result [class cur-properties])))
                              result))
        opts (assoc opts
                    :blocks-container-id (::blocks-container-id state)
                    :hover? @*hover?)]
    (when-not (and (empty? own-properties)
                   (empty? class->properties)
                   (not new-property?)
                   (not (:page-configure? opts)))
      [:div.ls-properties-area (cond->
                                {:on-mouse-over #(reset! *hover? true)
                                 :on-mouse-out #(reset! *hover? false)}
                                 (:selected? opts)
                                 (assoc :class "select-none"))
       (properties-section block (if class-schema? properties own-properties) opts)

       (when (and (seq full-hidden-properties) (not class-schema?) (not config/publishing?))
         (hidden-properties block full-hidden-properties opts))

       (when (or new-property? (not in-block-container?))
         (new-property block edit-input-id properties new-property? opts))

       (when (and (seq class->properties) (not one-class?))
         (let [page-cp (:page-cp opts)]
           [:div.parent-properties.flex.flex-1.flex-col.gap-1
            (for [[class class-properties] class->properties]
              (let [id-properties (->> class-properties
                                       remove-built-in-properties
                                       (map (fn [id] [id (get block-properties id)])))]
                (when (seq id-properties)
                  [:div
                   (when page-cp
                     [:span.text-sm (page-cp {} class)])
                   (properties-section block id-properties opts)])))]))])))