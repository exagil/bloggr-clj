(ns bloggr-clj.posts.db-test
  (:use clojure.test
        ring.mock.request)
  (:require [bloggr-clj.posts.post :as post-record]
            [bloggr-clj.posts.db :as posts-db]))

(def db-spec {:dbtype "postgresql"
              :dbname "bloggr_clj_test"
              :host "localhost"
              :user "chi6rag"})

(deftest test-that-it-fetches-no-posts-when-none-exist
  (is (= (posts-db/fetch-all-posts db-spec) '())))

(deftest test-that-it-fetches-posts-successfully-when-they-exist
  (def first-post (post-record/->Post "First Post Title" "First Post Body"))
  (def second-post (post-record/->Post "Second Post Title" "Second Post Body"))
  (def third-post (post-record/->Post "Third Post Title" "Third Post Body"))
  (def posts (lazy-seq [first-post second-post third-post]))
  (posts-db/save db-spec first-post)
  (posts-db/save db-spec second-post)
  (posts-db/save db-spec third-post)
  (is (= (posts-db/fetch-all-posts db-spec) posts)))

