(ns bloggr-clj.posts.db-test
  (:use clojure.test
        ring.mock.request)
  (:require [bloggr-clj.posts.post :as post-record]
            [bloggr-clj.posts.db :as posts-db]))

(deftest test-that-it-fetches-no-posts-when-none-exist
  (def db-spec {:dbtype "postgresql"
                :dbname "bloggr_clj_test"
                :host "localhost"
                :user "chi6rag"})
  (is (= (posts-db/fetch-all-posts db-spec) '())))

