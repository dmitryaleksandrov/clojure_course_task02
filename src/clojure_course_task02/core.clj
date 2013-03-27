(ns clojure-course-task02.core
  (:gen-class))

(import java.io.File)

(defn find-files [file-name path]
  (let [fpattern (re-pattern file-name)]
    (letfn [(find-in-dir [d]
              (let [fs (filter #(.isFile %) (.listFiles d))
                    ds (filter #(.isDirectory %) (.listFiles d))] 
                (apply concat 
                  (conj (map find-in-dir ds) 
                    (filter #(re-matches fpattern %)  
                      (conj (map #(.getName %) fs) (.getPath d)))))))]
    (let [result (find-in-dir (File. path))]
      (shutdown-agents)
      result))))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
