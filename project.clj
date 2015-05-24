(defproject api-endpoint "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [clj-time "0.9.0"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [korma "0.4.0"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [org.xerial/sqlite-jdbc "3.8.7"]
                 [mysql/mysql-connector-java "5.1.35"]
                 [org.clojure/data.json "0.2.6"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler api-endpoint.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
