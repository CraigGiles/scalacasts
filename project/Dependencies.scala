import sbt._

object Dependencies {
  // Versions
  val akkaVersion = "2.4.2"
  val logbackVersion = "1.0.9"
  val scalatestVersion = "2.2.4"

  val scalaTest = "org.scalatest" %% "scalatest" % scalatestVersion
  val logback = "ch.qos.logback" % "logback-classic" % logbackVersion

  // Projects
  val base = Seq(
    scalaTest % "it,test",
    logback
  )

  val backend = base ++ Seq(
  )

  val frontend = base ++ Seq(
  )
}
