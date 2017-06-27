(ns bloggr-clj.posts.handler
  (:gen-class)
  (:require [bloggr-clj.posts.db :as posts-db]
            [bloggr-clj.response.simple :as simple-response]
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
        successful-message "Post created successfully"
        erreneous-message "Failed to create post. Please try again after some time!"]
  (if (posts-db/save db-spec post)
    (simple-response/respond-with 200 successful-message)
    (simple-response/respond-with 500 erreneous-message))))
