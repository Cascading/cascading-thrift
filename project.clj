(defproject backtype/cascading-thrift "0.2.3"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-options {:debug "true" :fork "true"}
  :repositories {"conjars" "http://conjars.org/repo"}
  :dependencies [[cascading/cascading-hadoop "2.0.0-wip-259"
                  :exclusions [org.codehaus.janino/janino
                               org.apache.hadoop/hadoop-core]]
                 [org.apache.thrift/libthrift "0.6.1"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]])
