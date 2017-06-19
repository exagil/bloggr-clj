(ns bloggr-clj.router-test
  (:use clojure.test
        ring.mock.request
        bloggr-clj.router))

(deftest test-that-it-handles-unknown-paths-gracefully
  (is (= (bloggr-clj.router/handle-not-found (ring.mock.request/request :get "/"))
     {:status 404
      :headers {"Content-Type" "application/json"}
      :body "{\"status\":\"Not Found\"}"})))

