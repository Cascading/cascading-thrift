(defproject backtype/cascading-thrift "0.2.0"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-options {:debug "true" :fork "true"}
  :repositories {"conjars" "http://conjars.org/repo"}
  :dependencies [[cascading/cascading-hadoop "2.0.0-wip-166"
                  :exclusions [org.codehaus.janino/janino
                               org.apache.hadoop/hadoop-core]]
                 [backtype/thriftjava "1.0.0"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]])
