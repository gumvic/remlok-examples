(ns hello.core
  (:require
    [reagent.core :refer [render]]))

;;;;;;;;;;;
;; Hello ;;
;;;;;;;;;;;
;; Hello !!!

(defn root []
  [:span "Hello !!!"])

;;;;;;;;;;;;;;
;; Figwheel ;;
;;;;;;;;;;;;;;

(defn ^:export run []
  (render
    [root]
    (js/document.getElementById "app")))