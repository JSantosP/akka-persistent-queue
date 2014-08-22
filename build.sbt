name := "akka-persistent-queue"

scalaVersion := "2.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.4",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.4",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.4" % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.1",
  "org.scalatest" % "scalatest_2.11" % "2.1.6" % "test")
