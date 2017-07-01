(defproject bloggr-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.6.1"]
                 [ring/ring-mock "0.3.0"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.postgresql/postgresql "42.1.1"]
                 [org.clojure/data.json "0.2.6"]
                 [ring/ring-json "0.4.0"]
                 [peridot "0.4.4"]
                 [compojure "1.1.6"]]
  :target-path "target/%s"
  :main ^:skip-aot bloggr-clj.core
  :profiles {:uberjar {:aot :all}})
