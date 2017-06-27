(ns bloggr-clj.response.simple-test
  (:use clojure.test)
  (:gen-class)
  (:require [bloggr-clj.response.simple :as simple-response]))

(deftest test-that-it-knows-the-simple-schema-to-respond-with
  (is (= {:status 200
          :headers {"Content-Type" "application/json"}
          :body "{\"message\":\"Hello\"}"} (simple-response/respond-with 200 "Hello"))))

