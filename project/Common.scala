import sbt._
import sbt.Keys._

object Common {
  val commonSettings = Seq(
    // Organization Settings
    organization := "com.gilesc",
    version := "0.0.1",
    scalaVersion := "2.11.7",

    // Scalaform Settings
    Scalaform.scalaformSettings
  )

  def createProject(name: String) = {
    Project(name, file(name))
    .settings(commonSettings: _*)
  }
}
