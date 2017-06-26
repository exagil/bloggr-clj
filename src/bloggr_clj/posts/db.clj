(ns bloggr-clj.posts.db
  (:gen-class)
  (:require [clojure.java.jdbc :as jdbc]
            [bloggr-clj.posts.post :as post-record]))

(defn- process-fetched-post [post-row]
  (post-record/->Post (:title post-row) (:body post-row)))

(defn fetch-all-posts [db-spec]
  (jdbc/query db-spec ["SELECT * FROM posts"]
              {:row-fn process-fetched-post}))

(defn save [db-spec post]
  (jdbc/insert! db-spec :posts post))

