(ns address-book.models.query-defs
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]))

(defqueries "address_book/models/address_book_queries.sql" {:connection (env :database-url)})
