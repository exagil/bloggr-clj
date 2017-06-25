(ns bloggr-clj.server
  (:gen-class)
  (:use ring.adapter.jetty)
  (:use bloggr-clj.router)
  (:use bloggr-clj.logging))

(def app
  (-> bloggr-clj.router/configured
      bloggr-clj.logging/wrap-log-request))

