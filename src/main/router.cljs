(ns router
  (:require [routes]
            [views.grimorio :refer [grimorio]]
            [views.login :refer [login]]
            [reagent.core :as reagent]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]))

(defonce current-page (reagent/atom nil))

(defn init! []
  (rfe/start!
    (rf/router
      [["/"         {:name routes/login
                     :view login}]
       ["/grimorio" {:name routes/grimorio
                     :view grimorio}]])
  (fn [match] (reset! current-page match))
  {:use-fragment false}))

(defn app []
  (let [page @current-page]
    (if page
      [(:view (:data page))]
      [:div "..."])))
