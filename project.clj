(defproject backtype/cascading-thrift "0.2.5"
  :source-paths ["src/clj"]
  :min-lein-version "2.3.0"
  :java-source-paths ["src/jvm"]
  :repositories {"conjars" "http://conjars.org/repo"}
  :dependencies [[cascading/cascading-hadoop "2.0.0-wip-288"
                  :exclusions [org.codehaus.janino/janino
                               org.apache.hadoop/hadoop-core]]
                 [org.apache.thrift/libthrift "0.6.1"]]
  :profiles {:provided
             {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]]}})
