enablePlugins(JavaServerAppPackaging)

// Project settings
lazy val root = BuildUtils.rootProject.
  settings(unidocSettings: _*).
  settings(
    name := "scalacasts",
    aggregate in update := false,
    Revolver.settings
  ).
  aggregate(commons, backend, presentation)

lazy val backend = BuildUtils.createSubProject("backend").
  dependsOn(commons % "compile->compile;test->test").
  settings(
    AkkaDeps.settings,

    // Scalacasts specific settings
    libraryDependencies ++= Dependencies.backend
  ).
  configs(IntegrationTest).
  settings(Defaults.itSettings: _*)

lazy val frontend = BuildUtils.createSubProject("frontend").
  enablePlugins(play.PlayScala).
  configs(IntegrationTest).
  settings(Defaults.itSettings: _*).
  dependsOn(backend % "compile->compile").
  settings(
    // other settings
    libraryDependencies ++= Dependencies.frontend
  )

javaOptions in Universal ++= Seq(
  // JVM memory tuning
  "-J-Xmx1024m",
  "-J-Xms512m",

  // You may also want to include this setting if you use play evolutions
  "-DapplyEvolutions.default=true"
)
