(ns bloggr-clj.router
  (:gen-class)
  (:use bloggr-clj.ping)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]))

(defn handle-not-found [request]
  {:status 404
   :headers {"Content-Type" "application/json"}
   :body "{\"status\":\"Not Found\"}"})

(defroutes configured
  (GET "/ping" [] bloggr-clj.ping/handle)
  (route/not-found handle-not-found))
