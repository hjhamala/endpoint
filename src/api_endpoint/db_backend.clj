(ns api-endpoint.db-backend
  (:require [clojure.java.jdbc :as sql]))

(def sqlite-db {:subprotocol "sqlite"
                :subname "resources/endpoint.db"})

(def mysql-db {:subprotocol "mysql"
               :subname "//127.0.0.1:3306/endpoint"
               :user "endpoint"
               :password "endpoint"
               :delimiters "`"})

(def create-ad_statistics (   
 sql/create-table-ddl :ad_statistics
   [:ad_id "integer"]
   [:date "date"]
   [:impressions	 "bigint"]
   [:clicks "bigint"]
   [:spent "bigint"]))

(def create-ad_actions (   
 sql/create-table-ddl :ad_actions
   [:ad_id "integer"]
   [:date "varchar(64)"]
   [:action "char(162)"]
   [:count "varchar(255)"]
   [:value "varchar(255)"]))

(defn setup-database
  [db] 
   (sql/db-do-commands db 
                       create-ad_statistics
                       create-ad_actions))
