package com.gilesc.scalacasts.dataaccess

import slick.driver.JdbcProfile

import scala.concurrent.Future

trait DatabaseProfile {
  val profile: JdbcProfile
  import profile.api._

  val db = Database.forConfig("database")
  def execute[T](action: DBIO[T]): Future[T] = db.run(action)
}
