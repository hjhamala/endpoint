(ns api-endpoint.validators
  (:require [clj-time.format :as f]
            [clojure.string :as str]
            [api-endpoint.util :as u]))

(defn- validate-ids?
  [ids]
  (if (nil? ids)
    false
    (let [id-map (str/split ids #",")]
    (->> id-map
      (map u/get-int)
      (every? boolean)))))

(defn- ISO-8861-date?
  [date]
  (try
    (f/parse (f/formatters :date) date)
    true
    (catch Exception e false)))

(defn validate-stat-params?
  [ids start-date end-date]
  (if (every? boolean [(validate-ids? ids) (ISO-8861-date? start-date) (ISO-8861-date? end-date)])
    true
    false))