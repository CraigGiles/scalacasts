package com.gilesc.scalacasts.dataaccess

import com.typesafe.config.ConfigFactory
import slick.driver.{H2Driver, JdbcProfile}

import scala.concurrent.Future

trait DatabaseProfile {
  val profile: JdbcProfile = {
    val config = ConfigFactory.load()
    config.getString("scalacasts.database.profile") match {
      case "mysql" => MySqlDatabaseDriver
      case _ => H2Driver
    }
  }

  import profile.api._

  val db = Database.forConfig("scalacasts.database")
  def execute[T](action: DBIO[T]): Future[T] = db.run(action)

}
