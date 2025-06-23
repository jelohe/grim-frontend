(ns backend-stub.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes POST GET OPTIONS]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.cors :refer [wrap-cors]]
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
    (if (not= email "test@example.com")
      (-> (response {:token "fake-jwt-token-123"})
          (status 201))
      (-> (response {:error "Email already in use"})
          (status 422)))))

(defn mock-get-note [name]
  (fn [req]
    (let [token (get-in req [:headers "authorization"])]
      (cond
        (not= token "Bearer fake-jwt-token-123")
        (-> (response {:error "Unauthorized"})
            (status 422))

        (= name "first-note")
        (-> (response {:name "first note"
                       :content "This is the content of the first note"})
            (status 200))

        :else
        (-> (response {:error "Not found"})
            (status 404))))))

(defroutes app-routes
  (POST "/api/login" [] mock-login)
  (POST "/api/signup" [] mock-signup)
  (GET "/api/notes/:name" [name] (mock-get-note name))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)
      (wrap-cors :access-control-allow-origin [#"http://localhost:3000"]
                 :access-control-allow-methods [:get :put :post :delete])
      ))

(defn -main []
  (run-jetty app {:port 3001 :join? false})
  (println)
  (println "Server running on http://localhost:3001 🚀"))
