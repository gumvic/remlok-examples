(ns counter.dev
  (:require [counter.core :as example]
            [figwheel.client :as fw]))

(fw/start {:on-jsload example/run
           :websocket-url "ws://localhost:3449/figwheel-ws"})