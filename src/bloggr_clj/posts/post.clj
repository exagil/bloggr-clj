(ns bloggr-clj.posts.post)

(defrecord Post [title body])

(defn errors [record]
  (if (clojure.string/blank? (:title record))
    ["Title is blank"]
    []))
