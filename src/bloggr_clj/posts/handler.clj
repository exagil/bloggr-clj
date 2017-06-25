(ns bloggr-clj.posts.handler
  (:gen-class))

(defn all [request]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body "[]"})
