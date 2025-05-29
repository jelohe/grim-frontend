(ns grimorio.app.core
  (:require [reagent.core :as r]
            [cljs.pprint :as pp]
            [reagent.dom :as rdom]))

;; --- STATE ---
(def opened-notes (r/atom [{:name "a note" 
                            :value "this is the note body"
                            :selected true}
                           {:name "another note"
                            :value "a note body\n\nwith\n\nnewlines"
                            :selected false}
                           {:name "more notes"
                            :value "more notes\r\nwith more content"
                            :selected false}
                           ]))

(add-watch opened-notes :opened-notes
           (fn [key _atom _old-state new-state]
             (println "---" key "atom changed ---")
             (pp/pprint new-state)))

;; --- VIEWS ---
(defn header []
  [:header
   [:h1 "Grimorio"]
   [:div.searchbar-wrapper
    [:input {:class "searchbar" :placeholder "search..."}]]])

(defn sidebar []
  [:section.sidebar
   [:ul
    (for [note @opened-notes
          :let [note-name (:name note)]]
      ^{:key note-name}
      [:li
       {:class    (when (:selected note) "selected")
        :on-click #(swap! opened-notes
                          (fn [notes]
                            (mapv (fn [n]
                                    (assoc n :selected (= (:name n) note-name)))
                                  notes)))}
       note-name])]])

(defn content []
  [:section.note-content
   [:h2 "Docker en linux sin sudo"]
    [:br]
    [:p "Por defecto docker en linux necesita ser ejecutado como super usuario."
      [:br]
      [:br]
      "Para evitar este comportamiento, hay que crear un grupo `docker`:"
      [:br]
      "`groupadd docker`"
      [:br]
      [:br]
      "Despues hay que añadir nuestro user al nuevo grupo:"
      [:br]
      "`usermod -a -G docker my-user`"
      [:br]
      [:br]
      "*Recuerda cerrar y abrir la sesion para que los cambios surjan efecto.*"]])

(defn grimorio-app []
  [:<> 
   (header)
   [:main
    (sidebar)
    (content)]])


;; --- RENDER ---
(defn render []
  (rdom/render [grimorio-app] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
