(ns edn_validator_cljcode.compare
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io])
  (:require [clojure.java.io :as io] [clojure.edn :as edn])
  (:require [clojure.set :as set])
  (:require [clojure.data.json :as json])
  
   (:gen-class :main true
    :name edn_validator_cljcode.compare
    :methods [#^{:static true} [codding [String String] String]]
    )
    )



(defn codding [jsonfile ednfile]
  
  ;;Fetching the json file data and editing the names of measures and storing as json-rec
  (def all-jrec (json/read-str(slurp jsonfile)))
  (def json-rec (into #{}
        (for [ x (get all-jrec "measures")] 
          (clojure.string/replace (get x "fullyQualifiedName") #"/" "."))))
  
  ;;Fetching the edn file data and editing the names of measures as edn-rec
  (def all-erec (clojure.string/replace (slurp  ednfile) #"#synapse.clinical.programs.ProgramDef" " "))
  (def w (clojure.core/read-string (str " " all-erec " ")))
  (def edn-rec(into #{} (for [y (get w :measures)] 
                       (clojure.string/replace (get y :name) #"/" "."))))
  
  ;;comparing the edn and json obtained names 
  ;;The unique files of edn 
  (def edndata ( clojure.set/difference edn-rec json-rec))
  ;;The unique files of json
  (def jsondata ( clojure.set/difference json-rec edn-rec))
  
  ;;Checking  files which contain the data and then it will be written to a text file 
  (try (cond 
         (and (empty? edndata) (empty? jsondata))(spit "C:\\Users\\se056529\\Documents\\ednvalidator-result.txt" "No Data To Display" :append true)
         (empty? edndata) ((spit "C:\\Users\\se056529\\Documents\\ednvalidator-result.txt" "\n The json data is \n" :append true)
                           (spit "C:\\Users\\se056529\\Documents\\ednvalidator-result.txt" jsondata :append true))
         (empty? jsondata) ((spit "C:\\Users\\se056529\\Documents\\ednvalidator-result.txt" "\n The edn data is \n" :append true)
                            (spit "C:\\Users\\se056529\\Documents\\ednvalidator-result.txt" edndata :append true))
          :else ( (spit "C:\\Users\\se056529\\Documents\\ednvalidator-result.txt" "\n The unique data is \n" :append true)
                    (spit "C:\\Users\\se056529\\Documents\\ednvalidator-result.txt" edndata :append true)
                    (spit "C:\\Users\\se056529\\Documents\\ednvalidator-result.txt" jsondata :append true))
          )
     (catch Exception e (println (str " " (.getMessage e)))))

 ;;Returning a string 
 (str "done" "") 
 )


(defn -codding [x y]
  (codding x y)
  )

;;To run on REPL remove the below commented semicolons
(-codding "C:\\Users\\se056529\\Documents\\choc1.json" "C:\\Users\\se056529\\Downloads\\app-rules-5.0.0\\algorithms\\registry-programs\\cernerstandard.adolescentwellness.clinical.program_ruleset_description.edn")


(defn -main []
  )

