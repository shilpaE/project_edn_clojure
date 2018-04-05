(ns edn_validator_cljcode.testing
   (:require [clojure.java.io :as io])
  (:require [clojure.set :as set])
  (:require [edn_validator_cljcode.compare :refer :all]))
 (use 'clojure.test)
 
 
(deftest codding-test
 
  (is (= "redo" (codding "aaaaa.tfyg" "hgdtfyguhik.yg")))
  (is (= "false" (codding "C:\\Users\\se056529\\Documents\\choc1.json" "C:\\Users\\se056529\\Downloads\\app-rules-5.0.0\\algorithms\\registry-programs\\cernerstandard.adolescentwellness.clinical.program_ruleset_description.edn")))
  (is (not= "false" (codding "aaaaa.tfyg" "hgdtfyguhik.yg")))
  (is (= "redo" (codding "1" "2")))
 )

(run-tests 'edn_validator_cljcode.testing)
