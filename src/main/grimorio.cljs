(ns grimorio
  (:require [state]
            [router]
            [reagent.dom :as rdom]
            [reagent.core :as reagent]
            [components.header :refer [header]]
            [components.sidebar :refer [sidebar]]
            [components.content :refer [content]]
            ))

(defn render! []
  (rdom/render [router/app] (js/document.getElementById "root")))

(defn init! []
  (router/init!)
  (render!))

(defn ^:dev/after-load reload! []
  (render!))
