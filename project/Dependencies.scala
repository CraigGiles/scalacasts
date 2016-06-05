import sbt._

object Dependencies {
  // Versions
  val akkaVersion = "2.4.2"
  val logbackVersion = "1.0.9"
  val scalatestVersion = "2.2.4"
  val bcryptVersion = "0.3m"
  val quillVersion = "0.6.1"
  val slickVersion = "3.1.1"
  val mysqlConnectorVersion = "5.1.36"

  val scalaTest = "org.scalatest" %% "scalatest" % scalatestVersion
  val logback = "ch.qos.logback" % "logback-classic" % logbackVersion
  val bcrypt = "org.mindrot" % "jbcrypt" % bcryptVersion
  val mysqlConnector = "mysql" % "mysql-connector-java" % mysqlConnectorVersion
  val slick = "com.typesafe.slick" %% "slick" % slickVersion
  val slickCodegen = "com.typesafe.slick" %% "slick-codegen" % slickVersion
  val slf4j = "org.slf4j" % "slf4j-nop" % "1.6.4"

  // Projects
  val base = Seq(
    bcrypt,
    scalaTest % "it,test",
    logback
  )

  val database = Seq(
    mysqlConnector,
    slick,
    slickCodegen,
    "com.h2database" % "h2" % "1.4.191",
    "com.zaxxer" % "HikariCP" % "2.4.1",
    "com.typesafe.slick" %% "slick" % "3.1.1",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.1.1",
    "com.github.tototoshi" %% "slick-joda-mapper" % "2.1.0"
  )

  val backend = database ++ base ++ Seq(
    slf4j
  )

  val frontend = base ++ Seq(
  )
}
