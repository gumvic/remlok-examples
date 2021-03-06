(ns wiki.core
  (:import [goog Uri]
           [goog.net Jsonp])
  (:require
    [reagent.ratom :refer-macros [reaction]]
    [reagent.core :refer [render]]
    [remlok.loc :as l]
    [remlok.rem :as r]
    [cljs.core.async :as a :refer [chan put! take!]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Wiki Auto Completion ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Inspired by the om.next example of the same functionality.
;; Features:
;; 1) Remote reads.
;; 2) Remote physically located on the client.
;; 3) Asynchronous processing on the remote using core.async.
;; 4) Caching on the local, so that unnecessary remote reads are not made.

;;;;;;;;;;;;
;; Remote ;;
;;;;;;;;;;;;

(defn wiki [s]
  (let [uri "http://en.wikipedia.org/w/api.php?action=opensearch&format=json&search="
        gjsonp (Jsonp. (Uri. (str uri s)))
        ch (chan)]
    (.send gjsonp nil #(put! ch (second %)))
    ch))

(r/pub
  :sugg
  (fn [_ [_ search]]
    (wiki search)))

(defn receive [{:keys [reads]} res]
  (let [reads* (a/map
                 vector
                 (for [q reads
                       :let [ch (r/read nil q)]]
                   (let [ch* (chan)]
                     (take! ch #(put! ch* [q %]))
                     ch*)))]
    (take! reads* #(res %))))

;;;;;;;;;;;
;; Local ;;
;;;;;;;;;;;

(l/pub
  :search
  (fn [db _]
    {:loc (reaction
            (get @db :search))}))

(l/pub
  :sugg
  (fn [db [_ search]]
    {:loc
     (reaction
       (get-in @db [:sugg search]))
     :wiki
     (when
       (and
         (> (count search) 2)
         (not (get-in @db [:sugg search])))
       [:sugg search])}))

(l/mut
  :search
  (fn [db [_ search]]
    {:loc (assoc db :search search)}))

(l/merge
  :sugg
  (fn [db [_ search] data]
    (assoc-in db [:sugg search] data)))

(l/send
  :wiki
  (fn [_ req res]
    (println req)
    (receive req res)))

(defn input []
  (let [search (l/read [:search])]
    (fn []
      [:input
       {:on-change #(l/mut! [:search (-> % .-target .-value)])
        :value (str @search)}])))

(defn list []
  (let [search (l/read [:search])
        sugg (reaction
               @(l/read [:sugg @search]))]
    (fn []
      [:ul
       (for [s @sugg]
         ^{:key (str s)}
         [:li (str s)])])))

(defn root []
  [:div
   [input]
   [list]])

;;;;;;;;;;;;;;
;; Figwheel ;;
;;;;;;;;;;;;;;

(defn ^:export run []
  (render
    [root]
    (js/document.getElementById "app")))