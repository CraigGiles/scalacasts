enablePlugins(JavaServerAppPackaging)

// Project settings
lazy val root = (project in file(".")).
  settings(unidocSettings: _*).
  settings(
    aggregate in update := false
  ).
  aggregate(commons, core, presentation)

lazy val commons = Common.createProject("commons").
  settings(
    // other settings
    libraryDependencies ++= Dependencies.commons
  )

lazy val core = Common.createProject("core").
  dependsOn(commons % "compile->compile;test->test").
  settings(
    // other settings
    libraryDependencies ++= Dependencies.core
  )

lazy val presentation = Common.createProject("presentation").
  enablePlugins(play.PlayScala).
  dependsOn(core % "compile->compile").
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
