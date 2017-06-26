(ns bloggr-clj.posts.handler-test
  (:use clojure.test
        ring.mock.request
        bloggr-clj.posts.handler)
  (:require [clojure.data.json :as json]
            [bloggr-clj.posts.post :as post-record]
            [bloggr-clj.posts.db :as posts-db]))

(deftest test-that-the-posts-collection-is-empty-when-no-posts-exist
  (is (= (bloggr-clj.posts.handler/all (ring.mock.request/request :get "/posts/"))
      {:status 200
      :headers {"Content-Type" "application/json"}
      :body "[]"})))

(deftest test-that-the-posts-collection-exists-when-posts-are-present
  (def posts [(post-record/->Post "First Title" "First Body")
              (post-record/->Post "Second Title" "Second Body")])
  (with-redefs [posts-db/fetch-all-posts (fn [db-spec] posts)]
    (def expected-body (json/write-str posts))
    (is (= (bloggr-clj.posts.handler/all (ring.mock.request/request :get "/posts/"))
        {:status 200
        :headers {"Content-Type" "application/json"}
        :body expected-body}))))

