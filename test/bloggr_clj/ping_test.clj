(ns bloggr-clj.ping-test
  (:use clojure.test
        ring.mock.request
        bloggr-clj.ping))

(deftest test-that-it-response-to-ping-successfully
  (= (bloggr-clj.ping/handle (ring.mock.request/request :get "/ping"))
     {:status 200
      :headers {"Content-Type" "application/json"}
      :body "{\"response\":\"pong\"}"}))

