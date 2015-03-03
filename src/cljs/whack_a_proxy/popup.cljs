(ns whack-a-proxy.popup
  (:require [reagent.core :as reagent :refer [atom]]
            [whack-a-proxy.proxy :as proxy]))

(enable-console-print!)

;; -------------------------
;; Views

(defn main-view
  []
  [:div
   [:h2 "whack-a-proxy"]
   [:h4 "Status: " [:span (clj->js @proxy/current-state)]]
   [:button {:on-click proxy/toggle-proxy} (proxy/toggle-button-text)]])

;; -------------------------
;; Initialize popup
(defn ^:export run
  []
  (reagent/render [main-view] (.getElementById js/document "app")))
