(ns bloggr-clj.response.simple
  (:gen-class)
  (:require [clojure.data.json :as json]))

(defn- response [code message]
  {:status code
   :headers {"Content-Type" "application/json"}
   :body (json/write-str {:message message})})

(defn respond-with [code message]
  (response code message))

(defn bad []
  (response 400 "Bad Request"))

