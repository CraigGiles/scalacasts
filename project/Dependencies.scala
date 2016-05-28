import sbt._

object Dependencies {
  // Versions
  val akkaVersion = "2.4.2"
  val logbackVersion = "1.0.9"
  val scalatestVersion = "2.2.4"

  val scalaTest = "org.scalatest" %% "scalatest" % scalatestVersion
  val logback = "ch.qos.logback" % "logback-classic" % logbackVersion
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion

  // Projects
  val base = Seq(
    scalaTest % "it,test",
    logback
  )

  val backend = base ++ Seq(
    akkaActor,
    akkaRemote,
    akkaSlf4j,
    akkaTestkit % "test"
  )

  val frontend = base ++ Seq(
  )
}
