sbt-coffeescript
================

[![Build Status](https://github.com/sbt/sbt-coffeescript/actions/workflows/build-test.yml/badge.svg)](https://github.com/sbt/sbt-coffeescript/actions/workflows/build-test.yml)

An SBT plugin to compile [CoffeeScript](http://coffeescript.org/) sources to JavaScript.

To use this plugin use the addSbtPlugin command within your `project/plugins.sbt` file:

    addSbtPlugin("com.github.sbt" % "sbt-coffeescript" % "2.0.0")

Your project's build file also needs to enable sbt-web plugins. For example with build.sbt:

    lazy val root = (project in file(".")).enablePlugins(SbtWeb)

Once configured, any `*.coffee` or `*.litcoffee` files placed in `src/main/assets` will be compiled to JavaScript code in `target/web/public`.

Supported settings:

* `sourceMap` When set, generates sourceMap files. Defaults to `true`.

  `CoffeeScriptKeys.sourceMap := true`

* `bare` When set, generates JavaScript without the [top-level function safety wrapper](http://coffeescript.org/#lexical-scope). Defaults to `false`.

  `CoffeeScriptKeys.bare := false`

The plugin is built on top of [JavaScript Engine](https://github.com/sbt/sbt-js-engine) which supports different JavaScript runtimes.
