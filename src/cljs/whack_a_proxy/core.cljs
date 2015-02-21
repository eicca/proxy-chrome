(ns whack-a-proxy.core
    (:require [reagent.core :as reagent :refer [atom]]
              [cljsjs.react :as react]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to whack-a-proxy"]
   [:div [:a {:href "#/about"} "go to about page"]]])

;; -------------------------
;; Initialize app
(defn init! []
  (reagent/render [home-page] (.getElementById js/document "app")))
