(ns grimorio.app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

;; --- VIEWS ---
(defn sidebar []
  [:section.sidebar 
   [:div
    [:input {:placeholder "search..."}]]
   [:ul
    [:li.selected "Docker en linux sin sudo"]
    [:li "Nota importante"]
    [:li "Aqui van mis passwords"]
    [:li "Como peinarse el pecho"]
    ]])

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
   [:header
    [:h1 "Grimorio."]]
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
