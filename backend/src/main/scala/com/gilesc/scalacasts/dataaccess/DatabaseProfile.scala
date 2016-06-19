package com.gilesc.scalacasts.dataaccess

import com.typesafe.config.{Config, ConfigFactory}
import slick.driver.{H2Driver, JdbcProfile}

import scala.concurrent.Future

trait DatabaseProfile {
  lazy val profile: JdbcProfile = load(ConfigFactory.load())

  import profile.api._

  val db = Database.forConfig("scalacasts.database")
  def execute[T](action: DBIO[T]): Future[T] = db.run(action)

  private[this] val load: Config => JdbcProfile = { config =>
    config.getString("scalacasts.database.profile") match {
      case "mysql" => MySqlDatabaseDriver
      case _ => H2Driver
    }
  }
}
