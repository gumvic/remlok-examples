(ns counter.core
  (:require
    [reagent.core :refer [render]]
    [remlok.loc :refer [pub read mut mut!]]))

;;;;;;;;;;;;;
;; Counter ;;
;;;;;;;;;;;;;
;; Features local reads and mutations.

(pub
  :counter
  (fn [db _]
    {:loc db}))

(mut
  :dec
  (fn [db _]
    {:loc (dec db)}))

(mut
  :inc
  (fn [db _]
    {:loc (inc db)}))

(defn root []
  (let [counter (read [:counter])]
    (fn []
      [:div
       [:button {:on-click #(mut! [:dec])} "-"]
       [:span (str @counter)]
       [:button {:on-click #(mut! [:inc])} "+"]])))

;;;;;;;;;;;;;;
;; Figwheel ;;
;;;;;;;;;;;;;;

(defn ^:export run []
  (render
    [root]
    (js/document.getElementById "app")))