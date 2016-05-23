(defproject remlok-examples "0.3.0"
  :description "Examples for remlok"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [org.clojure/core.async "0.2.374"]
                 [reagent "0.5.1"]
                 [gumvic/remlok "0.3.0"]]
  :plugins [[lein-cljsbuild "1.1.2"]
            [lein-figwheel "0.5.3-1"]]
  :cljsbuild {:builds
              [{:id "hello"
                :source-paths ["src"]
                :figwheel true
                :compiler {:main "hello.core"
                           :output-dir "resources/public/js"
                           :output-to  "resources/public/js/client.js"
                           :asset-path "js"
                           :optimizations :none
                           :source-map true
                           :source-map-timestamp true}}
               {:id "counter"
                :source-paths ["src"]
                :figwheel true
                :compiler {:main "counter.core"
                           :output-dir "resources/public/js"
                           :output-to  "resources/public/js/client.js"
                           :asset-path "js"
                           :optimizations :none
                           :source-map true
                           :source-map-timestamp true}}
               {:id "wiki"
                :source-paths ["src"]
                :figwheel true
                :compiler {:main "wiki.core"
                           :output-dir "resources/public/js"
                           :output-to  "resources/public/js/client.js"
                           :asset-path "js"
                           :optimizations :none
                           :source-map true
                           :source-map-timestamp true}}
               {:id "board"
                :source-paths ["src"]
                :figwheel true
                :compiler {:main "board.core"
                           :output-dir "resources/public/js"
                           :output-to  "resources/public/js/client.js"
                           :asset-path "js"
                           :optimizations :none
                           :source-map true
                           :source-map-timestamp true}}]})
