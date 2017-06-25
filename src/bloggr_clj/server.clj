(ns bloggr-clj.server
  (:gen-class)
  (:use ring.adapter.jetty)
  (:use bloggr-clj.router)
  (:require [bloggr-clj.middlewares.logging :as log-middleware]))

(def app
  (-> bloggr-clj.router/configured
      log-middleware/wrap-log-request))

