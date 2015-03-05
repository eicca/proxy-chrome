(ns whack-a-proxy.proxy
    (:require [reagent.core :as reagent :refer [atom]]))

(def current-state (atom :disconnected))

;; TODO fetch from API
(def proxy-config
  {:mode "fixed_servers",
   :rules {:singleProxy {:scheme "http"
                         :host "xx.xx.xx.xx"
                         :port 3128}}})

;; TODO extern
(def proxy-settings (.. js/chrome -proxy -settings))

;; TODO extern
(defn- set-proxy
  [config callback]
  (.set proxy-settings
   (clj->js {:value config :scope "regular"})
   callback))

(defn- on-after-connect
  []
  (when-not (= @current-state :disconnected)
    (reset! current-state :online)))

(defn- connect!
  []
  (reset! current-state :connecting)
  (set-proxy proxy-config on-after-connect))

;; TODO extern
(defn- disconnect!
  []
  (.clear proxy-settings #js{})
  (reset! current-state :disconnected))

(defn- compose-initial-state
  [config]
  (case (get-in config ["value" "mode"])
    "fixed_servers" :online
    :disconnected))

(defn- set-initial-state
  [config]
  (reset! current-state (compose-initial-state config)))

(defn toggle-proxy
  []
  (case @current-state
    :disconnected (connect!)
    (:connecting :online) (disconnect!)))

(defn toggle-button-text
  []
  (case @current-state
    :disconnected "Connect to proxy"
    "Disconnect"))

;; TODO extern
(defn init
  [callback]
  (.get proxy-settings #js{:incognito false}
        (fn [config]
          (set-initial-state (js->clj config))
          (callback))))

;; Watchers

;; TODO extern
(defn- set-badge-color
  [color]
  ((.. js/chrome -browserAction -setBadgeBackgroundColor) (clj->js {:color color})))

;; TODO extern
(defn- set-badge-text
  [text]
  ((.. js/chrome -browserAction -setBadgeText) (clj->js {:text text})))

(defn- state-to-badge-text
  [state]
  (case state
    :disconnected "D"
    :connecting "C"
    :online "O"))

(defn- state-to-badge-color
  [state]
  (case state
    :disconnected "#FF4136"
    :connecting "#FFDC00"
    :online "#2ECC40"))

(add-watch current-state :popup-icon
  (fn [_ _ _ new-state]
    (set-badge-text (state-to-badge-text new-state))
    (set-badge-color (state-to-badge-color new-state))))
