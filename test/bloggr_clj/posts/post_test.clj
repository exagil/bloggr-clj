(ns bloggr-clj.posts.post-test
  (:require [bloggr-clj.posts.post :as post-record]
            [clojure.test :refer :all])
  (:gen-class))

(deftest test-that-post-has-no-errors-when-details-are-valid
  (let [post (post-record/->Post "Valid Title" "Valid Message")]
  (is (= '() (post-record/errors post)))))

(deftest test-that-post-has-a-title-blank-error-when-it-is-blank
  (let [post (post-record/->Post "" "Valid Message")]
  (is (= '("Title is blank") (post-record/errors post)))))

(deftest test-that-post-has-a-body-blank-error-when-it-is-blank
  (let [post (post-record/->Post "Valid Title" "")]
  (is (= '("Body is blank") (post-record/errors post)))))
