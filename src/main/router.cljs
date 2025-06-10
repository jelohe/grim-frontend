(ns router
  (:require [components.header :refer [header]]
            [components.sidebar :refer [sidebar]]
            [components.content :refer [content]]
            [reagent.core :as reagent]
            [reagent.dom :as rdom]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]))

(defonce current-page (reagent/atom nil))

(defn handle-login [event]
  (.preventDefault event)
  (rfe/push-state ::grimorio))

(defn login []
  [:div
   [:form
     [:h1 "Grimorio"]
     [:input {:id "email" 
              :placeholder "Email"
              :type "text"}]
     [:input {:id "password" 
              :placeholder "Password"
              :type "password"}]
     [:input {:value "Open"
              :type "submit"
              :on-click handle-login}]]])

(defn grimorio []
  (let [notes @state/opened-notes]
    [:<>
     (header)
     [:main
      (sidebar notes)
      (content (state/selected-note notes))]]))

(def routes
  [["/"         {:name ::login
                 :view login}]
   ["/grimorio" {:name ::grimorio
                 :view grimorio}]])

(defn init! []
  (rfe/start!
    (rf/router routes)
    (fn [match] (reset! current-page match))
    {:use-fragment false}))

(defn app []
  (let [page @current-page]
    (if page
      [(:view (:data page))]
      [:div "..."])))
