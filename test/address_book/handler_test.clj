(ns address-book.handler-test
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [address-book.handler :refer :all]
            [address-book.models.query-defs :as query]))

(facts "Example GET and POST tests"
  (with-state-changes [(before :facts (query/create-contacts-table-if-not-exists!))
                       (after :facts (query/drop-contacts-table!))]

  (fact "Test GET"
    (query/insert-contact<! {:name "JT" :phone "(321)" :email "JT@JT.com"})
    (query/insert-contact<! {:name "Utah" :phone "(432)" :email "JT@Other.com"})
    (let [response (app (mock/request :get "/"))]
      (:status response) => 200
      (:body response) => (contains "<div class=\"column-1\">JT</div>")
      (:body response) => (contains "<div class=\"column-1\">Utah</div>")))

  (fact "Test POST"
    (count (query/all-contacts)) => 0
    (let [response (app (mock/request :post "/post"  {:name "Bodhi" :phone "555-7890" :email "bells@beach.com"}))]
      (:status response) => 302
      (count (query/all-contacts)) => 1))))
