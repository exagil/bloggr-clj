(ns bloggr-clj.core
  (:gen-class)
  (:use ring.adapter.jetty)
  (:use bloggr-clj.router))

(defn -main [& args]
  (run-jetty bloggr-clj.router/configured {:port 3000}))
