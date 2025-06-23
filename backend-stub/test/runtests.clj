(ns runtests
  (:require [clojure.test :refer :all]
            [core-test]))

(defn -main []
  (run-tests 'core-test))
