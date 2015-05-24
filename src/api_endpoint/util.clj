(ns api-endpoint.util)

(defn get-int [str]
  "Returns nil if str is not a number"
  (try (Integer/parseInt str)
    (catch NumberFormatException _)))

(defn ok 
  [d] 
  {:status 200 :body d :headers {"Content-Type" "application/json"}})

(defn save-ok 
  [d] 
  {:status 201 :body d :headers {"Content-Type" "application/json"}})

(defn bad-request 
  [d] 
  {:status 400 :body d :headers {"Content-Type" "application/json"}})


(defn unauthorized 
  [d] 
  {:status 401 :body d :headers {"Content-Type" "application/json"}})

(defn forbidden 
  [d] 
  {:status 403 :body d :headers {"Content-Type" "application/json"}})

(defn not-found
  [d]
  {:status 404 :body d :headers {"Content-Type" "application/json"}})

(defn update-failed
  [d]
  {:status 422 :body d :headers {"Content-Type" "application/json"}})

(defn validation-error
  [d]
  {:status 400 :body d :headers {"Content-Type" "application/json"}})

