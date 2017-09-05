name := "NetworkRouting"

version := "1.0"

scalaVersion := "2.11.4"

mainClass in (Compile, run) := Some("com.yogesh.routing.RoutingApp")

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"