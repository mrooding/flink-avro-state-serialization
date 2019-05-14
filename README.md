# flink-avro-state-serialization

Sample project showcasing how to use Apache Flink custom serializers to support state schema migration using Apache Avro.

## Avrohugger generation

There's a branch called `avro-generated` which contains the setup to generate Classes based on Avro schemas. It generates the classes during compilation (`sbt compile`) or you can explicitly force generation using `sbt avroScalaGenerateSpecific`.

Based on [sbt-avrohugger](https://github.com/julianpeeters/sbt-avrohugger)
