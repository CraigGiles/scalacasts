import Dependencies._

lazy val root = (project in file(".")).
  aggregate(commons, core).
  settings(
    aggregate in update := false
  )

lazy val commonSettings = Seq(
  organization := "com.gilesc",
  version := "0.0.1",
  scalaVersion := "2.11.7"
)

lazy val commons = (project in file("commons")).
  settings(commonSettings: _*).
  settings(
    // other settings
  )

lazy val core = (project in file("core")).
  dependsOn(commons % "compile->compile").
  settings(commonSettings: _*).
  settings(
    // other settings
    libraryDependencies ++= coreDeps
  )

lazy val presentation = (project in file("presentation")).
  enablePlugins(play.PlayScala).
  settings(commonSettings: _*).
  settings(
    // other settings
  )
