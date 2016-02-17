import sbt._
import sbt.Keys._

object Common {
  val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")
  val makeVersionProperties = taskKey[Seq[File]]("Creates a version.properties file we can find at runtime.")
  val commonSettings = Seq(
    // Organization Settings
    organization := "com.gilesc",
    version := "0.0.1",
    scalaVersion := "2.11.7",

    // Scalaform Settings
    Scalaform.scalaformSettings,

    makeVersionProperties := {
      val propFile = (resourceManaged in Compile).value / "version.properties"
      val content = "version=%s" format (gitHeadCommitSha.value)
      IO.write(propFile, content)
      Seq(propFile)
    },

    gitHeadCommitSha in ThisBuild := Process("git rev-parse HEAD").lines.head,


    resourceGenerators in Compile <+= makeVersionProperties
  )

  def createProject(name: String) = {
    Project(name, file(name))
    .settings(commonSettings: _*)
  }
}
