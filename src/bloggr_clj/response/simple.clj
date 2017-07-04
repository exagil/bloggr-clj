(ns bloggr-clj.response.simple
  (:gen-class)
  (:require [clojure.data.json :as json]))

(defn- response-map [code body]
  {:status code :headers {"Content-Type" "application/json"} :body body})

(defn- response
  ([code message] (response-map code (json/write-str {:message message})))
  ([code message errors] (response-map code (json/write-str {:message message :errors errors})))
  ([code message errors map-to-include] (response-map code
                                              (json/write-str
                                                    (merge {:message message :errors errors} map-to-include)))))

(defn respond-with
  ([code message] (response code message))
  ([code message errors] (response code message errors))
  ([code message errors map-to-include] (response code message errors map-to-include)))

(defn bad []
  (response 400 "Bad Request"))

