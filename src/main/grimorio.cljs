(ns grimorio
  (:require [state]
            [components.header :refer [header]]
            [components.sidebar :refer [sidebar]]
            [components.content :refer [content]]
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
