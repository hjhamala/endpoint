(ns api-endpoint.korma-entities 
  (:require
    [api-endpoint.db-backend :as db]
    [korma.core :refer :all]
    [korma.db :refer :all]))

(defdb korma-db db/mysql-db)

(declare ad_actions ad_statistics)

(defentity ad_actions)

(defentity ad_statistics)

(defn statistics-with-actions
  [ids start_time end_time]
  (select ad_actions
          (join [(subselect ad_statistics 
                 (fields :ad_id "(round(clicks/impressions,4)) as ctr" "spent/clicks as cpc" "spent/(impressions/1000) as cpm") 
                 (aggregate (sum :impressions) :impressions )
                 (aggregate (sum :clicks) :clicks)
                 (aggregate (sum :spent) :spent)
                 (where {:date [>= start_time]})
                 (where {:date [<= end_time]})
                 (where {:ad_id [in ids]})
                 (group :ad_id)) :ad_statistics] (= :ad_statistics.ad_id :ad_actions.ad_id))
          (fields :ad_id 
                  :action 
                  :ad_statistics.impressions 
                  :ad_statistics.clicks 
                  :ad_statistics.spent 
                  :ad_statistics.ctr 
                  :ad_statistics.cpc 
                  :ad_statistics.cpm
                  "ad_statistics.spent/ad_actions.count as cpa")
          (aggregate (sum :count) :count )
          (aggregate (sum :value) :value)
          (where {:date [>= start_time]})
          (where {:date [<= end_time]})
          (where {:ad_id [in ids]})
          (group :action :ad_id)
          (order :ad_id)))


(defn reduce-actions
  [t]
(reduce (fn[a x](if (empty? a)
                  (assoc-in 
                    (select-keys x [:impressions :spent :clicks :ctr :cpc :cpm]) [:actions] {(keyword (:action x)) 
                                                                                             (select-keys x [:count  :value :cpa])})
                  (assoc-in a [:actions] (merge (:actions a) 
                                                {(keyword (:action x))(select-keys x [:count :value :cpa])}))))
                  [] t))

(defn get-and-filter-statistics
  [ids start_time end_time]
  (reduce (fn[x a](merge x {(keyword (str (first a))) (reduce-actions (second a))})) {} 
        (group-by :ad_id (statistics-with-actions ids start_time end_time))))
  