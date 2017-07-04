(ns bloggr-clj.posts.handler-test
  (:use clojure.test
        peridot.core
        bloggr-clj.server
        bloggr-clj.posts.handler)
  (:require [clojure.data.json :as json]
            [bloggr-clj.posts.post :as post-record]
            [ring.mock.request :as ring-mock-request]
            [bloggr-clj.posts.db :as posts-db]))

(deftest test-that-the-posts-collection-is-empty-when-no-posts-exist
  (with-redefs [posts-db/fetch-all-posts (fn [db-spec] '())]
  (is (= (bloggr-clj.posts.handler/all (ring-mock-request/request :get "/v1/posts/"))
      {:status 200
      :headers {"Content-Type" "application/json"}
      :body "[]"}))))

(deftest test-that-the-posts-collection-exists-when-posts-are-present
  (def posts [(post-record/->Post "First Title" "First Body")
              (post-record/->Post "Second Title" "Second Body")])
  (with-redefs [posts-db/fetch-all-posts (fn [db-spec] posts)]
    (let [expected-body (json/write-str posts)]
    (is (= (bloggr-clj.posts.handler/all (ring-mock-request/request :get "/v1/posts/"))
        {:status 200
        :headers {"Content-Type" "application/json"}
        :body expected-body})))))

(deftest test-that-post-is-created-successfully-when-a-valid-post-is-saved-successfully
  (with-redefs [posts-db/save (fn [db-spec posts] 1)]
    (let [expected-body "{\"message\":\"Post created successfully\",\"errors\":[],\"id\":1}"
          response (-> (session bloggr-clj.server/app)
                       (request "/v1/posts/" :request-method :post
                                             :body (json/write-str {:title "Sample Title" :body "Sample Body"})
                                             :content-type "application/json"))]
  (is (= 200 (get-in response [:response :status])))
  (is (= expected-body (get-in response [:response :body]))))))

(deftest test-that-post-is-not-created-successfully-when-saving-it-failed-due-to-database-issues
  (with-redefs [posts-db/save (fn [db-spec post] 0)]
    (let [expected-body "{\"message\":\"Failed to create post. Please try again after some time!\"}"
          response (-> (session bloggr-clj.server/app)
                       (request "/v1/posts/" :request-method :post
                                             :body (json/write-str {:title "Sample Title" :body "Sample Body"})
                                             :content-type "application/json"))]
  (is (= 500 (get-in response [:response :status])))
  (is (= expected-body (get-in response [:response :body]))))))

(deftest test-that-post-is-not-created-when-erroneous
  (let [expected-body "{\"message\":\"Failed to create post.\",\"errors\":[\"Title is blank\",\"Body is blank\"]}"
        response (-> (session bloggr-clj.server/app)
                     (request "/v1/posts/" :request-method :post
                                           :body (json/write-str {:title "" :body ""})
                                           :content-type "application/json"))]
  (is (= 422 (get-in response [:response :status])))
  (is (= expected-body (get-in response [:response :body])))))
