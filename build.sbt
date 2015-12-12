enablePlugins(JavaServerAppPackaging)

// Project settings
lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(unidocSettings: _*).
  settings(
    aggregate in update := false
  ).
  aggregate(commons, core, presentation)

lazy val commonSettings = Seq(
  // Organization Settings
  organization := "com.gilesc",
  version := "0.0.1",
  scalaVersion := "2.11.7",

  // Scalaform Settings
  Scalaform.scalaformSettings
)

lazy val commons = (project in file("commons")).
  settings(commonSettings: _*).
  settings(
    // other settings
    libraryDependencies ++= Dependencies.commons
  )

lazy val core = (project in file("core")).
  dependsOn(commons % "compile->compile;test->test").
  settings(commonSettings: _*).
  settings(
    // other settings
    libraryDependencies ++= Dependencies.core
  )

lazy val presentation = (project in file("presentation")).
  enablePlugins(play.PlayScala).
  dependsOn(core % "compile->compile").
  settings(commonSettings: _*).
  settings(
    // other settings
    libraryDependencies ++= Dependencies.presentation
  )

javaOptions in Universal ++= Seq(
  // JVM memory tuning
  "-J-Xmx1024m",
  "-J-Xms512m",

  // You may also want to include this setting if you use play evolutions
  "-DapplyEvolutions.default=true"
)
