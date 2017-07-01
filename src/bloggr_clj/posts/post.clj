(ns bloggr-clj.posts.post)

(defrecord Post [title body])

(defn presence-error-for [element-name entry]
  (if (clojure.string/blank? entry)
    [(str element-name " is blank")]
    []))

(defn- title-errors [record]
  (presence-error-for "Title" (:title record)))

(defn- body-errors [record]
  (presence-error-for "Body" (:body record)))

(defn errors [record]
  (concat '() (title-errors record) (body-errors record)))

