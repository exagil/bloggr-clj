(ns bloggr-clj.posts.handler
  (:gen-class)
  (:require [bloggr-clj.posts.db :as posts-db]
            [clojure.data.json :as json]))

(def db-spec {:dbtype "postgresql"
              :dbname "bloggr_clj_test"
              :host "localhost"
              :user "chi6rag"})

(defn all [request]
  (def fetched-posts (posts-db/fetch-all-posts db-spec))
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str fetched-posts)})

(defn create [request]
  (let [post (json/read-str (slurp (:body request)))
        successful-response "{\"status\":\"OK\",\"message\":\"Post created successfully\"}"
        erreneous-response "{\"status\":\"InternalServerError\",\"message\":\"Failed to create post. Please try again after some time!\"}"]
  (if (posts-db/save db-spec post)
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body successful-response}
    {:status 500
     :headers {"Content-Type" "application/json"}
     :body erreneous-response})))
