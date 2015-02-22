(ns whack-a-proxy.popup
    (:require [reagent.core :as reagent :refer [atom]]
              [cljsjs.react :as react]))

;; -------------------------
;; Views

(defn main-view
  []
  [:div [:h2 "Welcome to whack-a-proxy"]])


;; -------------------------
;; Initialize popup
(defn ^:export run
  []
  (reagent/render [main-view] (.getElementById js/document "app")))
