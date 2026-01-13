lazy val scala212 = "2.12.21"
lazy val scala3 = "3.7.4"
ThisBuild / crossScalaVersions := Seq(scala212, scala3)

lazy val `sbt-coffeescript` = project in file(".")

enablePlugins(SbtWebBase)

developers += Developer(
  "playframework",
  "The Play Framework Team",
  "contact@playframework.com",
  url("https://github.com/playframework")
)

addSbtJsEngine("1.4.0-M1")
addSbtWeb("1.6.0-M1")

libraryDependencies ++= Seq(
  "org.webjars.npm" % "node-require-fallback" % "1.0.0",
  "org.webjars.npm" % "coffeescript" % "2.7.0", // sync with src/main/resources/coffee.js
  "org.webjars" % "mkdirp" % "0.5.0", // sync with src/main/resources/coffee.js
)

// Customise sbt-dynver's behaviour to make it work with tags which aren't v-prefixed
ThisBuild / dynverVTagPrefix := false

// Sanity-check: assert that version comes from a tag (e.g. not a too-shallow clone)
// https://github.com/dwijnand/sbt-dynver/#sanity-checking-the-version
Global / onLoad := (Global / onLoad).value.andThen { s =>
  dynverAssertTagVersion.value
  s
}

ThisBuild / (pluginCrossBuild / sbtVersion) := {
  scalaBinaryVersion.value match {
    case "2.12" => "1.12.0"
    case _      => "2.0.0-RC8"
  }
}

scalacOptions := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, _)) => Seq("-Xsource:3")
    case _            => Seq.empty
  }
}
