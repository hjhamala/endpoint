(ns api-endpoint.api
  (:require [api-endpoint.util :as u]
            [api-endpoint.validators :as v]
            [api-endpoint.korma-entities :as korma]
            [clojure.string :as str]))

(defn get-stats
  [ids start-date end-date]
  (if (v/validate-stat-params? ids start-date end-date)
    (u/ok (korma/get-and-filter-statistics (str/split ids #",") start-date end-date))
    (u/bad-request {:error "Very bad, very bad indeed"})))