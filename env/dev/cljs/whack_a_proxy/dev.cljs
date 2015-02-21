(ns whack-a-proxy.dev
  (:require [whack-a-proxy.core :as core]
            [weasel.repl :as weasel]
            [reagent.core :as r]))

(enable-console-print!)

;; (weasel/connect "ws://localhost:9001" :verbose true)

(core/init!)
