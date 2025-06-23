(ns core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [backend-stub.core :refer [app]]))

(def fake-token "fake-jwt-token-123")

(deftest login-tests
  (testing "Logs in successfully"
    (let [req (-> (mock/request :post "/api/login")
                  (mock/json-body {:email "test@example.com"
                                   :password "password123"}))
          res (app req)
          body (json/parse-string (:body res) true)]
      (is (= 200 (:status res)))
      (is (= fake-token (:token body)))))
  
  (testing "Password is invalid"
    (let [req (-> (mock/request :post "/api/login")
                  (mock/json-body {:email "test@example.com"
                                   :password "bad-password"}))
          res (app req)
          body (json/parse-string (:body res) true)]
      (is (= 401 (:status res)))
      (is (= "Invalid credentials" (:error body)))))
  )

(deftest signup-tests
  (testing "Creates a new user"
    (let [req (-> (mock/request :post "/api/signup")
                  (mock/json-body {:email "new@user.com"
                                   :password "new-password-123"}))
          res (app req)
          body (json/parse-string (:body res) true)]
      (is (= 201 (:status res)))
      (is (= fake-token (:token body)))))

  (testing "Email is duplicated"
    (let [req (-> (mock/request :post "/api/signup")
                  (mock/json-body {:email "test@example.com"
                                   :password "password123"}))
          res (app req)
          body (json/parse-string (:body res) true)]
      (is (= 422 (:status res)))
      (is (= "Email already in use" (:error body)))))
  )

(deftest note-tests
  (testing "Returns a note"
    (let [req (-> (mock/request :get "/api/notes/first-note")
                  (mock/header "authorization" (str "Bearer " fake-token)))
          res (app req)
          body (json/parse-string (:body res) true)]
      (is (= 200 (:status res)))
      (is (= "first note" (:name body)))
      (is (= "This is the content of the first note" (:content body)))))

  (testing "Note is missing"
    (let [req (-> (mock/request :get "/api/notes/missing-note")
                  (mock/header "authorization" (str "Bearer " fake-token)))
          res (app req)
          body (json/parse-string (:body res) true)]
      (is (= 404 (:status res)))
      (is (= "Not found" (:error body)))))

  (testing "User is not authorized"
    (let [req (-> (mock/request :get "/api/notes/first-note")
                  (mock/header "authorization" "Bearer wrong-token"))
          res (app req)
          body (json/parse-string (:body res) true)]
      (is (= 422 (:status res)))
      (is (= "Unauthorized" (:error body)))))
  )
