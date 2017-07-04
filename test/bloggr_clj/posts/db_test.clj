(ns bloggr-clj.posts.db-test
  (:require [clojure.java.jdbc :as jdbc]
            [bloggr-clj.posts.post :as post-record]
            [clojure.test :refer :all]
            [bloggr-clj.posts.db :as posts-db]))

(def db-spec {:dbtype "postgresql"
              :dbname "bloggr_clj_test"
              :host "localhost"
              :user "chi6rag"})

(def invalid-db-spec {:dbtype "postgresql"
                      :dbname "invalid_db_name"
                      :host "localhost"})

(defn- assert-no-posts-exist []
  (is (= 0
         (:count (first (jdbc/query db-spec ["SELECT count(*) FROM posts"]))))))

(defn- once [fn]
  (jdbc/execute! db-spec "truncate table posts")
  (fn)
  (jdbc/execute! db-spec "truncate table posts"))

(defn- each [fn]
  (jdbc/execute! db-spec "truncate table posts")
  (fn))

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

(deftest test-that-it-handles-database-errors-gracefully-while-fetching-posts
  (is (= (posts-db/fetch-all-posts invalid-db-spec) '())))

(deftest test-that-it-does-not-save-post-in-case-of-db-error
  (def first-post (post-record/->Post "First Post Title" "First Post Body"))
  (is (= (posts-db/save invalid-db-spec first-post) 0)))

(deftest test-that-it-knows-how-to-save-a-post
  (def first-post (post-record/->Post "First Post Title" "First Post Body"))
  (is (not (= (posts-db/save db-spec first-post) 0))))

(deftest test-that-it-knows-how-to-delete-a-post-successfully
  (let [first-post (post-record/->Post "First Post Title" "First Post Body")
        first-post-id (posts-db/save db-spec first-post)]
  (is (= true (posts-db/delete db-spec first-post-id))))
  (assert-no-posts-exist))

(deftest test-that-it-knows-how-fail-gracefully-when-deletion-of-post-fails-due-to-db-error
  (let [first-post (post-record/->Post "First Post Title" "First Post Body")
        first-post-id (:id (posts-db/save db-spec first-post))]
  (is (= false (posts-db/delete invalid-db-spec first-post-id)))))

(use-fixtures :each each)
(use-fixtures :once once)
