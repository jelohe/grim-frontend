(ns grimorio
  (:require [state]
            [components.header :refer [header]]
            [reagent.core :as reagent]
            [components.sidebar :refer [sidebar]]
            [components.content :refer [content]]
            [reitit.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.spec :as rss]
            [reagent.dom :as rdom]))

(defn login []
  [:div
   [:p "Work in progress :)"]])

(defn grimorio []
  (let [notes @state/opened-notes]
    [:<>
     (header)
     [:main
      (sidebar notes)
      (content (state/selected-note notes))]]))

(defonce current-page (reagent/atom nil))

(def routes
  [["/"         {:name ::login
                 :view login}]
   ["/grimorio" {:name ::grimorio
                 :view grimorio}]])

(defn init-routes! []
  (rfe/start!
    (rf/router routes)
    (fn [match]
      (reset! current-page match))
    {:use-fragment false}))

(defn app []
  (let [page @current-page]
    (if page
      [(:view (:data page))]
      [:div "Loading..."])))

(defn render! []
  (rdom/render [app] (js/document.getElementById "root")))

(defn init! []
  (init-routes!)
  (render!))

(defn ^:dev/after-load reload! []
  (render!))
