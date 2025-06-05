(ns state
  (:require [reagent.core :as r]
            [cljs.pprint :as pp]))

(defn create-state [initial-state]
  (r/atom initial-state))

(def test-state
  [{:name "a note"
    :content "this is the note body"
    :selected true}
   {:name "another note"
    :content "a note body\n\nwith\n\nnewlines"
    :selected false}
   {:name "more notes"
    :content "more notes\r\nwith more content"
    :selected false}])

(def opened-notes (create-state test-state))

(add-watch opened-notes :opened-notes
           (fn [key _atom _old-state new-state]
             (println "~~" key "atom changed ~~")
             (pp/pprint new-state)))

(defn select-note! [note-name]
  (swap! opened-notes
         (fn [notes]
           (mapv (fn [n]
                   (assoc n :selected (= (:name n) note-name)))
                 notes))))

(defn selected-note [notes]
  (first (filter :selected notes)))
