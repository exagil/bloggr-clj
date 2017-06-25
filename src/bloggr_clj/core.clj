(ns bloggr-clj.core
  (:gen-class)
  (:use ring.adapter.jetty)
  (:use bloggr-clj.server))

(defn -main [& args]
  (run-jetty bloggr-clj.server/app {:port 3000}))
