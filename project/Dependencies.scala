import sbt._

object Dependencies {
  lazy val versions = Map[String, String](
    "logback" -> "1.0.9",
    "scalaTest" -> "2.2.4"
  )

  val scalaTest = "org.scalatest" %% "scalatest" % versions("scalaTest")
  val logback = "ch.qos.logback" % "logback-classic" % versions("logback")

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
