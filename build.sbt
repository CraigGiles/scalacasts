enablePlugins(JavaServerAppPackaging)

// Project settings
lazy val root = (project in file(".")).
  settings(unidocSettings: _*).
  settings(
    aggregate in update := false
  ).
  aggregate(commons, backend, presentation)

lazy val commons = Common.createProject("commons").
  settings(
    AkkaDeps.settings,

    // commons specific settings
    libraryDependencies ++= Dependencies.commons
  )

lazy val backend = Common.createProject("backend").
  dependsOn(commons % "compile->compile;test->test").
  settings(
    AkkaDeps.settings,

    // Scalacasts specific settings
    libraryDependencies ++= Dependencies.core
  )

lazy val presentation = Common.createProject("presentation").
  enablePlugins(play.PlayScala).
  dependsOn(backend % "compile->compile").
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
