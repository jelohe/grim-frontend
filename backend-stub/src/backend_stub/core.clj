(ns backend-stub.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes POST]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response status]]))

(defn mock-login [req]
  (let [{:keys [email password]} (:body req)]
    (if (= [email password] ["test@example.com" "password123"])
      (-> (response {:token "fake-jwt-token-123"})
          (status 200))
      (-> (response {:error "Invalid credentials"})
          (status 401)))))

(defn mock-signup [req]
  (let [{:keys [email]} (:body req)]
    (if (= email "test@example.com")
      (-> (response {:error "Email already in use"})
          (status 422))
      (-> (response {:token "fake-jwt-token-123"})
          (status 200)))))

(defroutes app-routes
  (POST "/api/login" [] mock-login)
  (POST "/api/signup" [] mock-signup)
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      wrap-json-response))

(defn -main []
  (run-jetty app {:port 3001 :join? false}))
