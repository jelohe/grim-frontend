(ns views.login
  (:require [reitit.frontend.easy :as rfe]
            [reagent.core :as r]
            [ajax.core :refer [POST]]
            [ajax.json :as json]
            [routes]))

(defonce email (r/atom ""))
(defonce password (r/atom ""))

(defn handle-login-click [event]
  (.preventDefault event)
  (POST "http://localhost:3001/api/login"
        {:params {:email @email
                  :password @password}
         :format (json/json-request-format)
         :handler (fn [] (rfe/push-state routes/grimorio))
         :error-handler (fn [] (js/console.log "Errorino"))}))

(defn login []
  [:div
   [:form
     {:on-submit handle-login-click}
     [:h1 "Grimorio"]
     [:input {:id "email"
              :placeholder "Email"
              :type "text"
              :on-change #(reset! email (-> % .-target .-value))}]
     [:input {:id "password"
              :placeholder "Password"
              :type "password"
              :on-change #(reset! password (-> % .-target .-value))}]
     [:input {:value "Open"
              :type "submit"}]]])
