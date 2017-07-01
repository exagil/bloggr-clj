(ns bloggr-clj.response.simple
  (:gen-class)
  (:require [clojure.data.json :as json]))

(defn respond-with [code message]
  {:status code
   :headers {"Content-Type" "application/json"}
   :body (json/write-str {:message message})})

(defn bad []
  {:status 400
   :headers {"Content-Type" "application/json"}
   :body (json/write-str {:message "Bad Request"})})
