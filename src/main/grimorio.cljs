(ns main.grimorio
  (:require [main.state :as state]
            [main.components.header :refer [header]]
            [main.components.sidebar :refer [sidebar]]
            [main.components.content :refer [content]]
            [reagent.dom :as rdom]))

(defn grimorio []
  (let [notes @state/opened-notes]
    [:<>
     (header)
     [:main
      (sidebar notes)
      (content (state/selected-note notes))]]))

(defn render! []
  (rdom/render [grimorio] (.getElementById js/document "root")))

(defn init! [] (render!))

(defn ^:dev/after-load reload! [] (render!))
