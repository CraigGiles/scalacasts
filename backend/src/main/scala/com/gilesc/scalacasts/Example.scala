package com.gilesc.scalacasts

import com.gilesc.scalacasts.dataaccess.UserRepository
import slick.driver.MySQLDriver

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

object Example extends App {
  //  val database = new DatabaseLayer(MySQLDriver)
  val repo = new UserRepository(MySQLDriver)
  val user = repo.findByEmail("my@email.com")
  val result = Await.result(user, 10 seconds)
  println("FOUND: " + result)
}
