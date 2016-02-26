import sbt._
import sbt.Keys._
import com.gilesc._

object BuildUtils {
  val commonSettings = Seq(
    // Organization Settings
    organization := "com.gilesc",
    version := "0.0.1",
    scalaVersion := "2.11.7",

    // Scalaform Settings
    Scalaform.scalaformSettings
  )

  lazy val rootProject = (project in file(".")).
    settings(commonSettings: _*)

  def createSubProject(name: String) = {
    Project(name, file(name)).
    settings(commonSettings: _*)
  }
}
