import com.gilesc._

enablePlugins(JavaServerAppPackaging)

// Project settings
lazy val root = GilescSbt.rootProject.
  settings(unidocSettings: _*).
  settings(
    name := "scalacasts",
    aggregate in update := false,
    Revolver.settings
  ).
  aggregate(commons, backend, presentation)

lazy val commons = GilescSbt.createSubProject("commons").
  settings(
    AkkaDeps.settings,

    libraryDependencies ++= Dependencies.backend
  )

lazy val backend = GilescSbt.createSubProject("backend").
  dependsOn(commons % "compile->compile;test->test").
  settings(
    AkkaDeps.settings,

    // Scalacasts specific settings
    libraryDependencies ++= Dependencies.backend
  )

lazy val presentation = GilescSbt.createSubProject("presentation").
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
