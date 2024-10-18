(ns frontend.extensions.pdf.assets
  (:require [cljs.reader :as reader]
            [clojure.string :as string]
            [frontend.config :as config]
            [frontend.db.conn :as conn]
            [frontend.db :as db]
            [frontend.db.model :as db-model]
            [frontend.db.utils :as db-utils]
            [frontend.db.async :as db-async]
            [frontend.fs :as fs]
            [frontend.handler.editor :as editor-handler]
            [frontend.handler.property :as property-handler]
            [frontend.handler.page :as page-handler]
            [frontend.handler.assets :as assets-handler]
            [frontend.handler.notification :as notification]
            [frontend.handler.property.util :as pu]
            [frontend.ui :as ui]
            [frontend.context.i18n :refer [t]]
            [frontend.extensions.lightbox :as lightbox]
            [frontend.state :as state]
            [frontend.util :as util]
            [logseq.publishing.db :as publish-db]
            [frontend.extensions.pdf.windows :as pdf-windows]
            [logseq.common.path :as path]
            [logseq.common.config :as common-config]
            [logseq.common.util.block-ref :as block-ref]
            [medley.core :as medley]
            [promesa.core :as p]
            [reitit.frontend.easy :as rfe]
            [rum.core :as rum]
            [fipp.edn :refer [pprint]]))

(defn get-in-repo-assets-full-filename
  [url]
  (let [repo-dir (config/get-repo-dir (state/get-current-repo))]
    (when (some-> url (string/trim) (string/includes? repo-dir))
      (some-> (string/split url repo-dir)
              (last)
              (string/replace-first "/assets/" "")))))

(defn inflate-asset
  [original-path & {:keys [href block]}]
  (let [web-link? (string/starts-with? original-path "http")
        blob-res? (some-> href (string/starts-with? "blob"))
        filename  (util/node-path.basename original-path)
        ext-name  "pdf"
        url       (if blob-res? href
                      (assets-handler/normalize-asset-resource-url original-path))
        filename' (if (or web-link? blob-res?) filename
                      (some-> (get-in-repo-assets-full-filename url)
                              (js/decodeURIComponent) (string/replace '"/" "_")))
        filekey   (util/safe-sanitize-file-name
                   (subs filename' 0 (- (count filename') (inc (count ext-name)))))]
    (when-let [key (and (not (string/blank? filekey))
                        (if web-link?
                          (str filekey "__" (hash url))
                          filekey))]
      {:key           key
       :block         block
       :identity      (subs key (- (count key) 15))
       :filename      filename
       :url           url
       :hls-file      (str "assets/" key ".edn")
       :original-path original-path})))

(defn resolve-area-image-file
  [img-stamp current {:keys [page id] :as _hl}]
  (when-let [key (:key current)]
    (-> common-config/local-assets-dir
        (str (if (config/db-based-graph?)
               (let [image-id (some-> id (db-utils/entity) :logseq.property.pdf/hl-image :block/uuid)]
                 (util/format "/%s.png" image-id))
               (util/format "/%s/%s_%s_%s.png" key page id img-stamp))))))

(defn file-based-ensure-ref-page!
  [pdf-current]
  ;; db version doesn't need a page for highlights data
  (when-not (config/db-based-graph? (state/get-current-repo))
    (when-let [page-name (util/trim-safe (:key pdf-current))]
      (p/let [page-name (str "hls__" page-name)
              repo (state/get-current-repo)
              page (db-async/<get-block repo page-name)
              file-path (:original-path pdf-current)
              format (state/get-preferred-format)
              repo-dir (config/get-repo-dir repo)
              asset-dir (util/node-path.join repo-dir common-config/local-assets-dir)
              url (if (string/includes? file-path asset-dir)
                    (str ".." (last (string/split file-path repo-dir)))
                    file-path)]
        (if-not page
          (let [label (:filename pdf-current)]
            (p/do!
             (page-handler/<create! page-name {:redirect?        false :create-first-block? false
                                               :split-namespace? false
                                               :format           format
                                               :properties       {:file
                                                                  (case format
                                                                    :markdown
                                                                    (util/format "[%s](%s)" label url)

                                                                    :org
                                                                    (util/format "[[%s][%s]]" url label)

                                                                    url)
                                                                  :file-path
                                                                  url}})
             (db-model/get-page page-name)))

          (do
          ;; try to update file path
            (when (nil? (some-> page
                                (:block/properties)
                                :file-path))
              (property-handler/add-page-property! page-name :file-path url))
            page))))))

(defn file-based-ensure-ref-block!
  [pdf-current {:keys [id content page properties] :as hl} insert-opts]
  (p/let [ref-page (when pdf-current (file-based-ensure-ref-page! pdf-current))]
    (when ref-page
      (let [ref-block (db-model/query-block-by-uuid id)]
        (if-not (nil? (:block/title ref-block))
          (do
            (println "[existed ref block]" ref-block)
            ref-block)
          (let [text       (:text content)
                wrap-props #(if-let [stamp (:image content)]
                              (assoc %
                                     :hl-type :area
                                     :hl-stamp stamp)
                              %)
                db-base? (config/db-based-graph? (state/get-current-repo))
                props (cond->
                       {(pu/get-pid :logseq.property/ls-type)  :annotation
                        (pu/get-pid :logseq.property.pdf/hl-page)  page
                        (pu/get-pid :logseq.property.pdf/hl-color) (:color properties)}

                        db-base?
                        (assoc (pu/get-pid :logseq.property.pdf/hl-value) hl)

                        (not db-base?)
                         ;; force custom uuid
                        (assoc :id (if (string? id) (uuid id) id)))
                properties (wrap-props props)]
            (when (string? text)
              (editor-handler/api-insert-new-block!
               text (merge {:page        (:block/name ref-page)
                            :custom-uuid id
                            :properties properties}
                           insert-opts)))))))))

(defn db-based-ensure-ref-block!
  [pdf-current {:keys [id content page properties] :as hl} insert-opts]
  (when-let [pdf-block (:block pdf-current)]
    (let [ref-block (db-model/query-block-by-uuid id)]
      (if (:block/title ref-block)
        (do
          (println "[existed ref block]" ref-block)
          ref-block)
        (let [text       (:text content)
              properties (cond->
                          {:block/tags :logseq.class/Pdf-annotation
                           :logseq.property/ls-type  :annotation
                           :logseq.property.pdf/hl-color (:color properties)
                           :logseq.property/asset (:db/id pdf-block)
                           :logseq.property.pdf/hl-page  page
                           :logseq.property.pdf/hl-value hl}
                           (:image content)
                           (assoc :logseq.property.pdf/hl-type :area
                                  :logseq.property.pdf/hl-image (:image content)))]
          (when (string? text)
            (editor-handler/api-insert-new-block!
             text (merge {:block-uuid (:block/uuid pdf-block)
                          :sibling? false
                          :custom-uuid id
                          :properties properties}
                         (assoc insert-opts :edit-block? false)))))))))

(defn ensure-ref-block!
  [pdf-current hl insert-opts]
  (if (config/db-based-graph? (state/get-current-repo))
    (db-based-ensure-ref-block! pdf-current hl insert-opts)
    (file-based-ensure-ref-block! pdf-current hl insert-opts)))

(defn construct-highlights-from-hls-page
  [hls-page]
  (p/let [blocks (db-async/<get-page-all-blocks (:block/uuid hls-page))]
    {:highlights (keep :logseq.property.pdf/hl-value blocks)}))

(defn file-based-load-hls-data$
  [{:keys [hls-file]}]
  (when hls-file
    (let [repo (state/get-current-repo)
          repo-dir (config/get-repo-dir repo)
          db-base? (config/db-based-graph? repo)]
      (p/let [_    (fs/create-if-not-exists repo repo-dir hls-file "{:highlights []}")
              res  (fs/read-file repo-dir hls-file)
              data (if res (reader/read-string res) {})]
        (if db-base?
          (p/let [hls-page (file-based-ensure-ref-page! (state/get-current-pdf))]
            (construct-highlights-from-hls-page hls-page))
          data)))))

(defn file-based-persist-hls-data$
  [{:keys [hls-file]} highlights extra]
  (when hls-file
    (let [repo-cur (state/get-current-repo)
          repo-dir (config/get-repo-dir repo-cur)
          data     (with-out-str (pprint {:highlights highlights :extra extra}))]
      (fs/write-file! repo-cur repo-dir hls-file data {:skip-compare? true}))))

(defn file-based-resolve-hls-data-by-key$
  [target-key]
  ;; TODO: fuzzy match
  (when-let [hls-file (and target-key (str common-config/local-assets-dir "/" target-key ".edn"))]
    (file-based-load-hls-data$ {:hls-file hls-file})))

(defn area-highlight?
  [hl]
  (and hl (not (nil? (get-in hl [:content :image])))))

(defn- file-based-persist-hl-area-image
  [repo-url repo-dir current new-hl old-hl png]
  (p/let [_          (js/console.time :write-area-image)
          ^js png    (.arrayBuffer png)
          {:keys [key]} current
                                  ;; dir
          fstamp     (get-in new-hl [:content :image])
          old-fstamp (and old-hl (get-in old-hl [:content :image]))
          fname      (str (:page new-hl) "_" (:id new-hl))
          fdir       (str common-config/local-assets-dir "/" key)
          _          (fs/mkdir-if-not-exists (path/path-join repo-dir fdir))
          new-fpath  (str fdir "/" fname "_" fstamp ".png")
          old-fpath  (and old-fstamp (str fdir "/" fname "_" old-fstamp ".png"))
          _          (and old-fpath (fs/rename! repo-url old-fpath new-fpath))
          _          (fs/write-file! repo-url repo-dir new-fpath png {:skip-compare? true})]

    (js/console.timeEnd :write-area-image)))

(defn- db-based-persist-hl-area-image
  [repo png]
  (let [file (js/File. #js [png] "pdf area highlight.png")]
    (editor-handler/db-based-save-assets! repo [file])))

(defn- persist-hl-area-image
  [repo-url repo-dir current new-hl old-hl png]
  (if (config/db-based-graph?)
    (p/let [result (db-based-persist-hl-area-image repo-url png)]
      (first result))
    (file-based-persist-hl-area-image repo-url repo-dir current new-hl old-hl png)))

(defn persist-hl-area-image$
  "Save pdf highlight area image"
  [^js viewer current new-hl old-hl {:keys [top left width height]}]
  (when-let [^js canvas (and (:key current) (.-canvas (.getPageView viewer (dec (:page new-hl)))))]
    (let [^js doc     (.-ownerDocument canvas)
          ^js canvas' (.createElement doc "canvas")
          dpr         js/window.devicePixelRatio
          repo-url    (state/get-current-repo)
          repo-dir    (config/get-repo-dir repo-url)
          dw          (* dpr width)
          dh          (* dpr height)]

      (set! (. canvas' -width) dw)
      (set! (. canvas' -height) dh)

      (when-let [^js ctx (.getContext canvas' "2d" #js{:alpha false})]
        (set! (. ctx -imageSmoothingEnabled) false)
        (.drawImage
         ctx canvas
         (* left dpr) (* top dpr) (* width dpr) (* height dpr)
         0 0 dw dh)

        (js/Promise.
         (fn [resolve reject]
           (.toBlob canvas'
                    (fn [^js png]
                      (p/catch
                       (resolve (persist-hl-area-image repo-url repo-dir current new-hl old-hl png))
                       (fn [err]
                         (reject err)
                         (js/console.error "[write area image Error]" err)))))))))))

(defn update-hl-block!
  [highlight]
  (when-let [block (db-model/get-block-by-uuid (:id highlight))]
    (when-let [color (get-in highlight [:properties :color])]
      (let [k (pu/get-pid :logseq.property.pdf/hl-color)]
        (property-handler/set-block-property! (state/get-current-repo) (:block/uuid block) k color)))))

(defn unlink-hl-area-image$
  [^js _viewer current hl]
  (when-let [fkey (and (area-highlight? hl) (:key current))]
    (let [repo-cur (state/get-current-repo)
          repo-dir (config/get-repo-dir repo-cur)
          fstamp   (get-in hl [:content :image])
          fname    (str (:page hl) "_" (:id hl))
          fdir     (str common-config/local-assets-dir "/" fkey)
          fpath    (util/node-path.join repo-dir (str fdir "/" fname "_" fstamp ".png"))]

      (fs/unlink! repo-cur fpath {}))))

(defn del-ref-block!
  [{:keys [id]}]
  (when-let [block (db-model/get-block-by-uuid id)]
    (editor-handler/delete-block-aux! block)))

(defn copy-hl-ref!
  [highlight ^js viewer]
  (p/let [ref-block (ensure-ref-block! (state/get-current-pdf) highlight nil)]
    (when ref-block
      (util/copy-to-clipboard!
       (block-ref/->block-ref (:block/uuid ref-block))
       :owner-window (pdf-windows/resolve-own-window viewer)))))

(defn file-based-open-block-ref!
  [block]
  (let [id (:block/uuid block)
        page (db/entity (:db/id (:block/page block)))
        page-name (:block/title page)
        file-path (get-in block [:block/properties :file-path])
        hl-page (pu/get-block-property-value block :logseq.property.pdf/hl-page)
        hl-value (pu/get-block-property-value block :logseq.property.pdf/hl-value)]
    (when-let [target-key (and page-name (subs page-name 5))]
      (p/let [hls (file-based-resolve-hls-data-by-key$ target-key)
              hls (and hls (:highlights hls))
              file-path (or file-path (str "../assets/" target-key ".pdf"))]
        (if-let [matched (or (and hls (medley/find-first #(= id (:id %)) hls))
                             (if hl-page {:page hl-page}
                                 (when-let [page (some-> hl-value :page)] {:page page})))]
          (do
            (state/set-state! :pdf/ref-highlight matched)
            ;; open pdf viewer
            (state/set-current-pdf! (inflate-asset file-path)))
          (js/console.debug "[Unmatched highlight ref]" block))))))

(defn db-based-open-block-ref!
  [block]
  (let [hl-value (:logseq.property.pdf/hl-value block)
        asset (:logseq.property/asset block)
        file-path (str "../assets/" (:block/uuid asset) ".pdf")]
    (if asset
      (->
       (p/let [href (assets-handler/<make-asset-url file-path)]
         (state/set-state! :pdf/ref-highlight hl-value)
        ;; open pdf viewer
         (state/set-current-pdf! (inflate-asset file-path {:href href :block asset})))
       (p/catch (fn [error]
                  (js/console.error error))))
      (js/console.error "Pdf asset no longer exists"))))

(defn open-block-ref!
  [block]
  (if (config/db-based-graph? (state/get-current-repo))
    (db-based-open-block-ref! block)
    (file-based-open-block-ref! block)))

(defn goto-block-ref!
  [{:keys [id] :as hl}]
  (when id
    (p/do!
     (ensure-ref-block!
      (state/get-current-pdf) hl {:edit-block? false})
     (rfe/push-state :page {:name (str id)}))))

(defn goto-annotations-page!
  ([current] (goto-annotations-page! current nil))
  ([current id]
   (when current
     (if (config/db-based-graph?)
       (rfe/push-state :page {:name (:block/uuid (:block current))} (if id {:anchor (str "block-content-" + id)} nil))
       (when-let [e (some->> (:key current) (str "hls__") (db-model/get-page))]
         (rfe/push-state :page {:name (str (:block/uuid e))} (if id {:anchor (str "block-content-" + id)} nil)))))))

(defn open-lightbox
  [e]
  (let [images (js/document.querySelectorAll ".hl-area img")
        images (to-array images)
        images (if-not (= (count images) 1)
                 (let [^js image (.closest (.-target e) ".hl-area")
                       image     (. image querySelector "img")]
                   (->> images
                        (sort-by (juxt #(.-y %) #(.-x %)))
                        (split-with (complement #{image}))
                        reverse
                        (apply concat)))
                 images)
        images (for [^js it images] {:src (.-src it)
                                     :w   (.-naturalWidth it)
                                     :h   (.-naturalHeight it)})]

    (when (seq images)
      (lightbox/preview-images! images))))

(rum/defcs area-display <
  (rum/local nil ::src)
  [state block]
  (let [*src (::src state)]
    (when-let [asset-path' (and block (publish-db/get-area-block-asset-url
                                       (conn/get-db (state/get-current-repo))
                                       block
                                       (db-utils/pull (:db/id (:block/page block)))))]
      (when (nil? @*src)
        (p/let [asset-path (assets-handler/<make-asset-url asset-path')]
          (reset! *src asset-path)))
      (when @*src
        [:span.hl-area
         [:span.actions
          (when-not config/publishing?
            [:button.asset-action-btn.px-1
             {:title         (t :asset/copy)
              :tabIndex      "-1"
              :on-pointer-down util/stop
              :on-click      (fn [e]
                               (util/stop e)
                               (-> (util/copy-image-to-clipboard (common-config/remove-asset-protocol @*src))
                                   (p/then #(notification/show! "Copied!" :success))))}
             (ui/icon "copy")])

          [:button.asset-action-btn.px-1
           {:title         (t :asset/maximize)
            :tabIndex      "-1"
            :on-pointer-down util/stop
            :on-click      open-lightbox}

           (ui/icon "maximize")]]
         [:img {:src @*src}]]))))
