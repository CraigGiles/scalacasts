
enablePlugins(JavaServerAppPackaging)

// Project settings
lazy val root = BuildUtils.rootProject.
  settings(unidocSettings: _*).
  settings(
    name := "scalacasts",
    aggregate in update := false,
    Revolver.settings,
    resolvers += Resolver.jcenterRepo).
  aggregate(backend, presentation)

lazy val flyway = (project in file("flyway"))
  .enablePlugins(FlywayPlugin)
  .settings(
    libraryDependencies ++= Dependencies.database)

lazy val backend = BuildUtils.createSubProject("backend").
  settings(
    // Scalacasts specific settings
    libraryDependencies ++= Dependencies.backend,
    libraryDependencies ++= Dependencies.database)

lazy val presentation = BuildUtils.createSubProject("presentation").
  enablePlugins(play.PlayScala).
  configs(IntegrationTest).
  settings(Defaults.itSettings: _*).
  dependsOn(backend % "compile->compile").
  settings(
    // other settings
    libraryDependencies ++= Dependencies.frontend)

javaOptions in Universal ++= Seq(
  // JVM memory tuning
  "-J-Xmx1024m",
  "-J-Xms512m",

  // You may also want to include this setting if you use play evolutions
  "-DapplyEvolutions.default=true")
