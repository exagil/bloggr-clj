(ns bloggr-clj.posts.db
  (:gen-class)
  (:import (java.sql SQLException))
  (:require [clojure.java.jdbc :as jdbc]
            [bloggr-clj.posts.post :as post-record]))

(defn- process-fetched-post [post-row]
  (post-record/->Post (:title post-row) (:body post-row)))

(defn fetch-all-posts [db-spec]
  (try
    (jdbc/query db-spec ["SELECT * FROM posts"]
              {:row-fn process-fetched-post})
    (catch SQLException e '())))

(defn save [db-spec post]
  (try
    (:id (first (jdbc/insert! db-spec :posts post)))
    (catch SQLException e 0)))

(defn delete [db-spec post-id]
  (try
    (do
      (jdbc/execute! db-spec ["DELETE FROM posts WHERE id=?" post-id])
      true)
    (catch SQLException e false)))

