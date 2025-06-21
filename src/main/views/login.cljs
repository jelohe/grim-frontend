(ns views.login
  (:require [reitit.frontend.easy :as rfe]
            [routes]
            ))

(defn handle-login [event]
  (.preventDefault event)
  (rfe/push-state routes/grimorio))

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

