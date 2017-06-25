(ns bloggr-clj.posts.handler-test
  (:use clojure.test
        ring.mock.request
        bloggr-clj.posts.handler))

(deftest test-that-the-posts-collection-is-empty-when-no-posts-exist
  (is (= (bloggr-clj.posts.handler/all (ring.mock.request/request :get "/posts/"))
      {:status 200
      :headers {"Content-Type" "application/json"}
      :body "[]"})))

