(ns logseq.shui.ui
  (:require [logseq.shui.util :as util]
            [logseq.shui.icon.v2 :as icon-v2]
            [logseq.shui.toaster.core :as toaster-core]
            [logseq.shui.select.core :as select-core]
            [logseq.shui.select.multi :as select-multi]
            [logseq.shui.dialog.core :as dialog-core]
            [logseq.shui.popup.core :as popup-core]
            [logseq.shui.form.core :as form-core]))

(def button (util/lsui-wrap "Button" {:static? false}))
(def link (util/lsui-wrap "Link"))
(def tabler-icon icon-v2/root)

(def alert (util/lsui-wrap "Alert"))
(def alert-title (util/lsui-wrap "AlertTitle"))
(def alert-description (util/lsui-wrap "AlertDescription"))
(def slider (util/lsui-wrap "Slider"))
(def badge (util/lsui-wrap "Badge"))
(def skeleton (util/lsui-wrap "Skeleton"))
(def calendar (util/lsui-wrap "Calendar"))
(def input form-core/input)
(def textarea form-core/textarea)
(def switch form-core/switch)
(def checkbox form-core/checkbox)
(def radio-group form-core/radio-group)
(def radio-group-item form-core/radio-group-item)
(def popover popup-core/popover)
(def popover-trigger popup-core/popover-trigger)
(def popover-content popup-core/popover-content)

(def tooltip (util/lsui-wrap "Tooltip"))
(def tooltip-trigger (util/lsui-wrap "TooltipTrigger"))
(def tooltip-content (util/lsui-wrap "TooltipContent"))
(def tooltip-provider (util/lsui-wrap "TooltipProvider"))

(def card (util/lsui-wrap "Card"))
(def card-header (util/lsui-wrap "CardHeader"))
(def card-title (util/lsui-wrap "CardTitle"))
(def card-description (util/lsui-wrap "CardDescription"))
(def card-content (util/lsui-wrap "CardContent"))
(def card-footer (util/lsui-wrap "CardFooter"))

(def form-provider form-core/form-provider)
(def form-item form-core/form-item)
(def form-label form-core/form-label)
(def form-description form-core/form-description)
(def form-message form-core/form-message)
(def form-field form-core/form-field)
(def form-control form-core/form-control)

(def select select-core/select)
(def select-group select-core/select-group)
(def select-value select-core/select-value)
(def select-trigger select-core/select-trigger)
(def select-content select-core/select-content)
(def select-label select-core/select-label)
(def select-item select-core/select-item)
(def select-separator select-core/select-separator)
(def select-scroll-up-button select-core/select-scroll-up-button)
(def select-scroll-down-button select-core/select-scroll-down-button)

(def dropdown-menu popup-core/dropdown-menu)
(def dropdown-menu-trigger popup-core/dropdown-menu-trigger)
(def dropdown-menu-content popup-core/dropdown-menu-content)
(def dropdown-menu-group popup-core/dropdown-menu-group)
(def dropdown-menu-item popup-core/dropdown-menu-item)
(def dropdown-menu-checkbox-item popup-core/dropdown-menu-checkbox-item)
(def dropdown-menu-radio-group popup-core/dropdown-menu-radio-group)
(def dropdown-menu-radio-item popup-core/dropdown-menu-radio-item)
(def dropdown-menu-label popup-core/dropdown-menu-label)
(def dropdown-menu-separator popup-core/dropdown-menu-separator)
(def dropdown-menu-shortcut popup-core/dropdown-menu-shortcut)
(def dropdown-menu-portal popup-core/dropdown-menu-portal)
(def dropdown-menu-sub popup-core/dropdown-menu-sub)
(def dropdown-menu-sub-content popup-core/dropdown-menu-sub-content)
(def dropdown-menu-sub-trigger popup-core/dropdown-menu-sub-trigger)

(def context-menu (util/lsui-wrap "ContextMenu"))
(def context-menu-trigger (util/lsui-wrap "ContextMenuTrigger"))
(def context-menu-content (util/lsui-wrap "ContextMenuContent"))
(def context-menu-item (util/lsui-wrap "ContextMenuItem"))
(def context-menu-checkbox-item (util/lsui-wrap "ContextMenuCheckboxItem"))
(def context-menu-radio-item (util/lsui-wrap "ContextMenuRadioItem"))
(def context-menu-label (util/lsui-wrap "ContextMenuLabel"))
(def context-menu-separator (util/lsui-wrap "ContextMenuSeparator"))
(def context-menu-shortcut (util/lsui-wrap "ContextMenuShortcut"))
(def context-menu-group (util/lsui-wrap "ContextMenuGroup"))
(def context-menu-portal (util/lsui-wrap "ContextMenuPortal"))
(def context-menu-sub (util/lsui-wrap "ContextMenuSub"))
(def context-menu-sub-content (util/lsui-wrap "ContextMenuSubContent"))
(def context-menu-sub-trigger (util/lsui-wrap "ContextMenuSubTrigger"))
(def context-menu-radio-group (util/lsui-wrap "ContextMenuRadioGroup"))

(def dialog dialog-core/dialog)
(def dialog-portal dialog-core/dialog-portal)
(def dialog-overlay dialog-core/dialog-overlay)
(def dialog-close dialog-core/dialog-close)
(def dialog-trigger dialog-core/dialog-trigger)
(def dialog-content dialog-core/dialog-content)
(def dialog-header dialog-core/dialog-header)
(def dialog-footer dialog-core/dialog-footer)
(def dialog-title dialog-core/dialog-title)
(def dialog-description dialog-core/dialog-description)

(def toast! toaster-core/toast!)
(def toast-dismiss! toaster-core/dismiss!)
(def dialog-open! dialog-core/open!)
(def dialog-alert! dialog-core/alert!)
(def dialog-confirm! dialog-core/confirm!)
(def dialog-close! dialog-core/close!)
(def dialog-close-all! dialog-core/close-all!)
(def popup-show! popup-core/show!)
(def popup-hide! popup-core/hide!)
(def popup-hide-all! popup-core/hide-all!)

(def multi-select-content select-multi/x-select-content)