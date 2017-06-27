(ns bloggr-clj.posts.handler-test
  (:use clojure.test
        ring.mock.request
        bloggr-clj.posts.handler)
  (:require [clojure.data.json :as json]
            [bloggr-clj.posts.post :as post-record]
            [bloggr-clj.posts.db :as posts-db]))

(deftest test-that-the-posts-collection-is-empty-when-no-posts-exist
  (with-redefs [posts-db/fetch-all-posts (fn [db-spec] '())]
  (is (= (bloggr-clj.posts.handler/all (ring.mock.request/request :get "/v1/posts/"))
      {:status 200
      :headers {"Content-Type" "application/json"}
      :body "[]"}))))

(deftest test-that-the-posts-collection-exists-when-posts-are-present
  (def posts [(post-record/->Post "First Title" "First Body")
              (post-record/->Post "Second Title" "Second Body")])
  (with-redefs [posts-db/fetch-all-posts (fn [db-spec] posts)]
    (let [expected-body (json/write-str posts)]
    (is (= (bloggr-clj.posts.handler/all (ring.mock.request/request :get "/v1/posts/"))
        {:status 200
        :headers {"Content-Type" "application/json"}
        :body expected-body})))))

(deftest test-that-post-is-created-successfully-when-a-valid-post-is-saved-successfully
  (with-redefs [posts-db/save (fn [db-spec posts] true)]
    (let [expected-body "{\"status\":\"OK\",\"message\":\"Post created successfully\"}"]
    (def new-post-request (-> (ring.mock.request/request :post "/v1/posts/")
                          (ring.mock.request/content-type "application/json")
                          (ring.mock.request/body (json/write-str {:title "Sample Title" :body "Sample Body"}))))
    (is (= (bloggr-clj.posts.handler/create new-post-request)
           {:status 200
            :headers {"Content-Type" "application/json"}
            :body expected-body})))))

(deftest test-that-post-is-not-created-successfully-when-saving-it-failed-due-to-database-issues
  (with-redefs [posts-db/save (fn [db-spec post] false)]
    (let [expected-body "{\"status\":\"InternalServerError\",\"message\":\"Failed to create post. Please try again after some time!\"}"
          new-post-request (-> (ring.mock.request/request :post "/v1/posts/")
                           (ring.mock.request/content-type "application/json")
                           (ring.mock.request/body (json/write-str {:title "Sample Title" :body "Sample Body"})))]
    (is (= (bloggr-clj.posts.handler/create new-post-request)
           {:status 500
            :headers {"Content-Type" "application/json"}
            :body expected-body})))))
