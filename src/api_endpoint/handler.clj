(ns api-endpoint.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.mock.request :as mock]
            [compojure.handler :as handler]
            [api-endpoint.api :as api]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :as json]
            [clojure.data.json :as j]))

(defroutes app-routes
  (GET "/api/stats" [ad_ids start_time end_time] (api/get-stats ad_ids start_time end_time))
  (route/not-found "Not Found"))

(def app
  (-> 
    (handler/api 
        (-> 
          app-routes
          (wrap-defaults site-defaults)
          (json/wrap-json-response)))))

(defn start 
  [port]
  (jetty/run-jetty #'app {:port port
                          :join? false}))

