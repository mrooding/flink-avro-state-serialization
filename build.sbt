name := "flink-stateful-wordcount"

version := "0"

scalaVersion in ThisBuild := "2.11.11"

scalacOptions := Seq(
  "-encoding", "utf8",
  "-target:jvm-1.8",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  "-deprecation",
  "-Xlog-reflective-calls"
)

val flinkVersion = "1.8.0"

libraryDependencies += "org.apache.avro"         %    "avro"                                 % "1.8.2"
libraryDependencies += "org.apache.flink"        %%   "flink-streaming-scala"                % flinkVersion      % Provided
libraryDependencies += "org.apache.flink"        %    "flink-avro"                           % flinkVersion

// make run command include the provided dependencies
Compile / run  := Defaults.runTask(Compile / fullClasspath,
  Compile / run / mainClass,
  Compile / run / runner
).evaluated

// stays inside the sbt console when we press "ctrl-c" while a Flink programme executes with "run" or "runMain"
Compile / run / fork := true
Global / cancelable := true

// exclude Scala library from assembly
assembly / assemblyOption  := (assembly / assemblyOption).value.copy(includeScala = false)

sourceGenerators in Compile += (avroScalaGenerateSpecific in Compile).taskValue

watchSources ++= ((avroSourceDirectories in Compile).value ** "*.avsc").get
