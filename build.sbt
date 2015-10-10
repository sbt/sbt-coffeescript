sbtPlugin := true

organization := "com.typesafe.sbt"

name := "sbt-coffeescript"

version := "1.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions += "-feature"

libraryDependencies ++= Seq(
  "org.webjars" % "coffee-script-node" % "1.7.1",
  "org.webjars" % "mkdirp" % "0.3.5"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-js-engine" % "1.1.3")

publishMavenStyle := false

publishTo := {
  if (isSnapshot.value) Some(Classpaths.sbtPluginSnapshots)
  else Some(Classpaths.sbtPluginReleases)
}

scriptedSettings

scriptedLaunchOpts <+= version apply { v => s"-Dproject.version=$v" }
