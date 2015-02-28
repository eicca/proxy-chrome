(ns whack-a-proxy.popup
    (:require [reagent.core :as reagent :refer [atom]]
              [cljsjs.react :as react]))

(def ^:private states [:disconnected :connecting :connected])

(def current-state (atom :disconnected))

(defn- on-after-connect
  []
  (when-not (= @current-state :disconnected)
    (reset! current-state :connected)))

(defn- connect!
  []
  (reset! current-state :connecting)
  (js/setTimeout on-after-connect 2000))

(defn- disconnect!
  []
  (reset! current-state :disconnected))

(defn- toggle-proxy
  []
  (case @current-state
    :disconnected (connect!)
    (:connecting :connected) (disconnect!)))

(defn- toggle-button-text
  []
  (case @current-state
    :disconnected "Connect to proxy"
    "Disconnect"))

;; -------------------------
;; Views

(defn- main-view
  []
  [:div
   [:h2 "whack-a-proxy"]
   [:h4 "Status: " [:span (clj->js @current-state)]]
   [:button {:on-click toggle-proxy} (toggle-button-text)]])

;; -------------------------
;; Initialize popup
(defn ^:export run
  []
  (reagent/render [main-view] (.getElementById js/document "app")))
