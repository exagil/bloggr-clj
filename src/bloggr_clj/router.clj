(ns bloggr-clj.router
  (:gen-class)
  (:use bloggr-clj.ping)
  (:use bloggr-clj.posts.handler)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]))

(defn handle-not-found [request]
  {:status 404
   :headers {"Content-Type" "application/json"}
   :body "{\"status\":\"Not Found\"}"})

(defroutes configured
  (GET "/ping" [] bloggr-clj.ping/handle)
  (GET "/v1/posts/" [] bloggr-clj.posts.handler/all)
  (POST "/v1/posts/" [] bloggr-clj.posts.handler/create)
  (route/not-found handle-not-found))
