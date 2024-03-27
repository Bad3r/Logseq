(ns logseq.shui.popup.core
  (:require [rum.core :as rum]
            [logseq.shui.util :as util]
            [medley.core :as medley]
            [logseq.shui.util :refer [use-atom]]))

;; ui
(def button (util/lsui-wrap "Button"))
(def popover (util/lsui-wrap "Popover"))
(def popover-trigger (util/lsui-wrap "PopoverTrigger"))
(def popover-content (util/lsui-wrap "PopoverContent"))
(def popover-arrow (util/lsui-wrap "PopoverArrow"))
(def popover-close (util/lsui-wrap "PopoverClose"))
(def popover-remove-scroll (util/lsui-wrap "PopoverRemoveScroll"))
(def dropdown-menu (util/lsui-wrap "DropdownMenu"))
(def dropdown-menu-trigger (util/lsui-wrap "DropdownMenuTrigger"))
(def dropdown-menu-content (util/lsui-wrap "DropdownMenuContent"))
(def dropdown-menu-arrow (util/lsui-wrap "DropdownMenuArrow"))
(def dropdown-menu-group (util/lsui-wrap "DropdownMenuGroup"))
(def dropdown-menu-item (util/lsui-wrap "DropdownMenuItem"))
(def dropdown-menu-checkbox-item (util/lsui-wrap "DropdownMenuCheckboxItem"))
(def dropdown-menu-radio-group (util/lsui-wrap "DropdownMenuRadioGroup"))
(def dropdown-menu-radio-item (util/lsui-wrap "DropdownMenuRadioItem"))
(def dropdown-menu-label (util/lsui-wrap "DropdownMenuLabel"))
(def dropdown-menu-separator (util/lsui-wrap "DropdownMenuSeparator"))
(def dropdown-menu-shortcut (util/lsui-wrap "DropdownMenuShortcut"))
(def dropdown-menu-portal (util/lsui-wrap "DropdownMenuPortal"))
(def dropdown-menu-sub (util/lsui-wrap "DropdownMenuSub"))
(def dropdown-menu-sub-content (util/lsui-wrap "DropdownMenuSubContent"))
(def dropdown-menu-sub-trigger (util/lsui-wrap "DropdownMenuSubTrigger"))

;; {:id :open? false :content nil :position [0 0] :root-props nil :content-props nil}
(defonce ^:private *popups (atom []))
(defonce ^:private *id (atom 0))
(defonce ^:private gen-id #(reset! *id (inc @*id)))

(defn get-popup
  [id]
  (when id
    (some->> (medley/indexed @*popups)
      (filter #(= id (:id (second %)))) (first))))

(defn get-popups [] @*popups)

(defn upsert-popup!
  [config]
  (when-let [id (:id config)]
    (if-let [[index config'] (get-popup id)]
      (swap! *popups assoc index (merge config' config))
      (swap! *popups conj config))))

(defn update-popup!
  [id ks val]
  (when-let [[index config] (get-popup id)]
    (let [ks (if (coll? ks) ks [ks])
          config (if (nil? val)
                   (medley/dissoc-in config ks)
                   (assoc-in config ks val))]
      (swap! *popups assoc index config))))

(defn detach-popup!
  [id]
  (when-let [[index] (get-popup id)]
    (swap! *popups #(->> % (medley/remove-nth index) (vec)))))

(defn show!
  [^js event content & {:keys [id as-dropdown? as-content? align root-props content-props] :as opts}]
  (let [*target (volatile! nil)
        position (cond
                   (vector? event) event

                   (or (instance? js/MouseEvent (or (.-nativeEvent event) event))
                     (instance? js/goog.events.BrowserEvent event))
                   (do (vreset! *target (.-target (or (.-nativeEvent event) event)))
                       [(.-clientX event) (.-clientY event)])

                   (instance? js/Element event)
                   (let [^js rect (.getBoundingClientRect event)
                         left (.-left rect)
                         width (.-width rect)
                         height (.-height rect)
                         bottom (.-bottom rect)]
                     (do (vreset! *target event)
                         [(+ left (case (keyword align)
                                    :start 0
                                    :end width
                                    (/ width 2)))
                          (- bottom height) width height]))
                   :else [0 0])]
    (upsert-popup!
      (merge opts
        {:id (or id (gen-id)) :target (deref *target)
         :open? true :content content :position position
         :as-dropdown? as-dropdown?
         :as-content? as-content?
         :root-props root-props
         :content-props (cond-> content-props
                          (not (nil? align))
                          (assoc :align (name align)))}))))

(defn hide!
  ([] (when-let [id (some-> (get-popups) (last) :id)] (hide! id)))
  ([id]
   (update-popup! id :open? false)))

(defn hide-all!
  []
  (doseq [{:keys [id]} @*popups]
    (hide! id)))

(rum/defc x-popup
  [{:keys [id open? content position as-dropdown? as-content? force-popover?
           auto-side? auto-focus? target root-props content-props]
    :as _props}]
  (rum/use-effect!
    (fn []
      (when (false? open?)
        (js/setTimeout #(detach-popup! id) 128)
        (when (and auto-focus? target)
          (js/setTimeout #(.focus target) 256))))
    [open?])

  ;; disableOutsidePointerEvents
  ;(rum/use-effect!
  ;  (fn []
  ;    (when-not as-dropdown?
  ;      (let [^js style js/document.body.style
  ;            set-pointer-event! #(set! (. style -pointerEvents) %)
  ;            try-unset! #(when (nil? (seq @*popups))
  ;                          (set-pointer-event! nil))]
  ;        (if open?
  ;          (set-pointer-event! "none")
  ;          (try-unset!))
  ;        #(try-unset!))))
  ;  [open?])

  (when-let [[x y _ height] position]
    (let [popup-root (if (not force-popover?) dropdown-menu popover)
          popup-trigger (if (not force-popover?) dropdown-menu-trigger popover-trigger)
          popup-content (if (not force-popover?) dropdown-menu-content popover-content)
          auto-side-fn (fn []
                         (let [vh js/window.innerHeight
                               [th bh] [y (- vh (+ y height))]]
                           (if (> bh 280)
                             "bottom"
                             (if (> (- th bh) 100)
                               "top" "bottom"))))
          content-props (cond-> content-props
                          auto-side? (assoc :side (auto-side-fn)))]
      (popup-root
        (merge root-props {:open open?})
        (popup-trigger
          {:as-child true}
          (button {:class "overflow-hidden fixed p-0 opacity-0"
                   :style {:height (if (and (number? height)
                                         (> height 0))
                                     height 1)
                           :width 1
                           :top y
                           :left x}} ""))
        (let [content-props (cond-> (merge {:onEscapeKeyDown #(hide! id)
                                            :onPointerDownOutside #(hide! id)}
                                      content-props)
                              (and (not force-popover?)
                                (not as-dropdown?))
                              (assoc :on-key-down (fn [^js e]
                                                    (some-> content-props :on-key-down (apply [e]))
                                                    (set! (. e -defaultPrevented) true))
                                     :on-pointer-move #(set! (. % -defaultPrevented) true)))
              content (if (fn? content)
                        (content (cond-> {:id id}
                                   as-content?
                                   (assoc :content-props content-props))) content)]
          (if as-content?
            content
            (popup-content content-props content)))))))

(rum/defc install-popups
  < rum/static
  []
  (let [[popups _set-popups!] (use-atom *popups)]

    (rum/use-effect!
      (fn []
        (let [^js cls (.-classList js/document.documentElement)
              s "has-x-popups"]
          (if (and (counted? popups) (> (count popups) 0))
            (.add cls s) (.remove cls s))
          #(.remove cls s)))
      [popups])

    [:<>
     (for [config popups
           :when (and (map? config) (:id config))]
       (x-popup config))]))