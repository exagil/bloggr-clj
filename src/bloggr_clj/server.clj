(ns bloggr-clj.server
  (:gen-class)
  (:use ring.adapter.jetty)
  (:use bloggr-clj.router)
  (:require [bloggr-clj.middlewares.logging :as log-middleware]
            [ring.middleware.json :as ring-middleware-json]
            [bloggr-clj.response.simple :as simple-response]))

(def app
  (-> bloggr-clj.router/configured
      (ring-middleware-json/wrap-json-body {:malformed-response (simple-response/bad)})
      log-middleware/wrap-log-request))

