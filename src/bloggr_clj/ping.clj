(ns bloggr-clj.ping
  (:gen-class))

(defn handle [request]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body "{\"response\":\"pong\"}"})
