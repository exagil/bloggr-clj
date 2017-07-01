(ns bloggr-clj.server
  (:gen-class)
  (:use ring.adapter.jetty)
  (:use bloggr-clj.router)
  (:require [bloggr-clj.middlewares.logging :as log-middleware]
            [ring.middleware.json :as ring-middleware-json]
            [bloggr-clj.response.simple :as simple-response]))

(def app
  (let [handler (-> bloggr-clj.router/configured
                    log-middleware/wrap-log-request)]
  (ring.middleware.json/wrap-json-body handler {:keywords? true})))

