(ns whack-a-proxy.proxy
    (:require [reagent.core :as reagent :refer [atom]]))

(def current-state (atom :disconnected))

(defn- on-after-connect
  []
  (when-not (= @current-state :disconnected)
    (reset! current-state :online)))

(defn- connect!
  []
  (reset! current-state :connecting)
  (js/setTimeout on-after-connect 2000))

(defn- disconnect!
  []
  (reset! current-state :disconnected))

(defn toggle-proxy
  []
  (case @current-state
    :disconnected (connect!)
    (:connecting :online) (disconnect!)))

(defn- toggle-button-text
  []
  (case @current-state
    :disconnected "Connect to proxy"
    "Disconnect"))

;; Watchers


; TODO extern
(defn set-badge-color
  [color]
  ((.. js/chrome -browserAction -setBadgeBackgroundColor) (clj->js {:color color})))

; TODO extern
(defn set-badge-text
  [text]
  ((.. js/chrome -browserAction -setBadgeText) (clj->js {:text text})))

(defn state-to-badge-text
  [state]
  (case state
    :disconnected "D"
    :connecting "C"
    :online "O"))

(defn state-to-badge-color
  [state]
  (case state
    :disconnected "#FF4136"
    :connecting "#FFDC00"
    :online "#2ECC40"))

(add-watch current-state :popup-icon
  (fn [_ _ _ new-state]
    (set-badge-text (state-to-badge-text new-state))
    (set-badge-color (state-to-badge-color new-state))))
