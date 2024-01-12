package com.typesafe.sbt.coffeescript

import com.typesafe.sbt.jse.SbtJsTask
import sbt._
import com.typesafe.sbt.web.SbtWeb
import spray.json.{JsBoolean, JsObject}
import sbt.Keys._

object Import {

  object CoffeeScriptKeys {
    val coffeescript = TaskKey[Seq[File]]("coffeescript", "Invoke the CoffeeScript compiler.")

    val bare = SettingKey[Boolean]("coffeescript-bare", "Compiles JavaScript that isn't wrapped in a function.")
    val sourceMap = SettingKey[Boolean]("coffeescript-source-map", "Outputs a v3 sourcemap.")
  }

}

object SbtCoffeeScript extends AutoPlugin {

  override def requires = SbtJsTask

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import SbtJsTask.autoImport.JsTaskKeys._
  import autoImport.CoffeeScriptKeys._

  val coffeeScriptUnscopedSettings = Seq(

    includeFilter := "*.coffee" | "*.litcoffee",

    jsOptions := JsObject(
      "bare" -> JsBoolean(bare.value),
      "sourceMap" -> JsBoolean(sourceMap.value)
    ).toString()
  )

  override def buildSettings = inTask(coffeescript)(
    SbtJsTask.jsTaskSpecificUnscopedBuildSettings ++ Seq(
      moduleName := "coffeescript",
      shellFile := getClass.getClassLoader.getResource("coffee.js")

    )
  )

  override def projectSettings = Seq(
    bare := false,
    sourceMap := true

  ) ++ inTask(coffeescript)(
    SbtJsTask.jsTaskSpecificUnscopedProjectSettings ++
      inConfig(Assets)(coffeeScriptUnscopedSettings) ++
      inConfig(TestAssets)(coffeeScriptUnscopedSettings) ++
      Seq(
        Assets / taskMessage := "CoffeeScript compiling",
        TestAssets / taskMessage := "CoffeeScript test compiling"
      )
  ) ++ SbtJsTask.addJsSourceFileTasks(coffeescript) ++ Seq(
    Assets / coffeescript := (Assets / coffeescript).dependsOn(Assets / webModules).value,
    TestAssets / coffeescript := (TestAssets / coffeescript).dependsOn(TestAssets / webModules).value
  )

}
