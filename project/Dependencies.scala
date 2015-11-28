import sbt._

object Dependencies {
  lazy val versions = Map[String, String](
    "akka" -> "2.4.0",
    "scalaTest" -> "2.2.4"
  )

  // Libraries
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % versions("akka")
  val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % versions("akka")
  val scalaTest = "org.scalatest" %% "scalatest" % versions("scalaTest")

  // Projects
  val base = Seq(
    scalaTest % Test
  )

  val commons = base ++ Seq(
    akkaActor
  )

  val core = base ++ Seq(
    akkaActor
  )

  val presentation = base ++ Seq(
  )
}
