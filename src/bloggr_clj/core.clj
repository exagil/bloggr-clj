(ns bloggr-clj.core
  (:gen-class))

(use 'ring.adapter.jetty)

(defn app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello from Ring"}

(defn -main
  (run-jetty app-handler {:port 3000}))

