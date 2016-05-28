import sbt._
import sbt.Keys._

object BuildUtils {
  lazy val BehaviorTest = config("bt") extend(Test)

  val commonSettings = Seq(
    // Organization Settings
    organization := "com.gilesc",
    version := "0.0.1",
    scalaVersion := "2.11.7",

    // Scalaform Settings
    ScalaFormatter.settings
  )

  lazy val rootProject = (project in file(".")).
    configs(IntegrationTest)
    .configs(BehaviorTest)
    .settings(inConfig(BehaviorTest)(Defaults.testSettings) : _*)
    .settings(inConfig(IntegrationTest)(Defaults.itSettings) : _*)
    .settings(commonSettings: _*)

  def createSubProject(name: String) = {
    Project(name, file(name))
    .configs(IntegrationTest)
    .configs(BehaviorTest)
    .settings(inConfig(BehaviorTest)(Defaults.testSettings) : _*)
    .settings(inConfig(IntegrationTest)(Defaults.itSettings) : _*)
    .settings(commonSettings: _*)
  }
}
