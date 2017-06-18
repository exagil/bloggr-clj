(ns bloggr-clj.core
  (:gen-class)
  (:use ring.adapter.jetty)
  (:use bloggr-clj.ping))

(defn -main [& args]
  (run-jetty bloggr-clj.ping/handle {:port 3000}))
