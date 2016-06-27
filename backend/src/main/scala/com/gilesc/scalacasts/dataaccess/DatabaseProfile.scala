package com.gilesc.scalacasts.dataaccess

import com.typesafe.config.Config
import slick.driver.{H2Driver, JdbcProfile}

import scala.concurrent.{ExecutionContext, Future}

object DatabaseProfile {
  def apply(config: Config)(implicit ec: ExecutionContext): DatabaseProfile = {
    val profile = config.getString("scalacasts.database.profile") match {
      case "mysql" => MySqlDatabaseDriver
      case _ => H2Driver
    }

    new DatabaseProfile(profile)(ec)
  }
}

class DatabaseProfile(val profile: JdbcProfile)(implicit val ec: ExecutionContext) {
  import profile.api._

  val db = Database.forConfig("scalacasts.database")
  def execute[T](action: DBIO[T]): Future[T] = db.run(action)
}

//trait DatabaseProfile {
//  lazy val profile: JdbcProfile = load(ConfigFactory.load())
//  import profile.api._
//
//  val db = Database.forConfig("scalacasts.database")
//  def execute[T](action: DBIO[T]): Future[T] = db.run(action)
//
//  private[this] val load: Config => JdbcProfile = { config =>
//    config.getString("scalacasts.database.profile") match {
//      case "mysql" => MySqlDatabaseDriver
//      case _ => H2Driver
//    }
//  }
//}
