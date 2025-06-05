(ns components.sidebar
  (:require [state]))

(defn sidebar [notes]
  [:section.sidebar
   [:ul
    (for [note notes
          :let [note-name (:name note)]]
      ^{:key note-name}
      [:li
       {:class    (when (:selected note) "selected")
        :on-click #(state/select-note! note-name)}
       note-name])]])
