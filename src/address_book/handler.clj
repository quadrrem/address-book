(ns address-book.handler
  (:require [compojure.core :refer [defroutes routes]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [address-book.routes.address-book-routes :refer [address-book-routes]]
            [address-book.models.query-defs :as query]))

(defn init []
  (query/create-contacts-table-if-not-exists!)
  (println "Address book application is starting"))

(defroutes app-routes
  (route/not-found "Not Found"))

(def app
  (-> (routes address-book-routes app-routes)
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))
