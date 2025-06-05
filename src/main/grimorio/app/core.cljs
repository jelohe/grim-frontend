(ns grimorio.app.core
  (:require [reagent.core :as r]
            [cljs.pprint :as pp]
            [reagent.dom :as rdom]))

;; --- STATE ---
(def opened-notes (r/atom [{:name "a note" 
                            :content "this is the note body"
                            :selected true}
                           {:name "another note"
                            :content "a note body\n\nwith\n\nnewlines"
                            :selected false}
                           {:name "more notes"
                            :content "more notes\r\nwith more content"
                            :selected false}
                           ]))

(add-watch opened-notes :opened-notes
           (fn [key _atom _old-state new-state]
             (println "---" key "atom changed ---")
             (pp/pprint new-state)))

(defn select-note! [note-name]
  (swap! opened-notes
         (fn [notes]
           (mapv (fn [n]
                   (assoc n :selected (= (:name n) note-name)))
                 notes))))

(defn selected-note [notes]
  (first (filter :selected notes)))

;; --- VIEWS ---
(defn header []
  [:header
   [:h1 "Grimorio"]
   [:div.searchbar-wrapper
    [:input {:class "searchbar" :placeholder "search..."}]]])

(defn sidebar [notes]
  [:section.sidebar
   [:ul
    (for [note notes
          :let [note-name (:name note)]]
      ^{:key note-name}
      [:li
       {:class    (when (:selected note) "selected")
        :on-click #(select-note! note-name)}
       note-name])]])

(defn content [note]
  (let [note-content (:content note)]
    [:section.note-content note-content]))

(defn grimorio-app []
  (let [notes @opened-notes]
    [:<> 
     (header)
     [:main
      (sidebar notes)
      (content (selected-note notes))]]))


;; --- RENDER ---
(defn render []
  (rdom/render [grimorio-app] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
