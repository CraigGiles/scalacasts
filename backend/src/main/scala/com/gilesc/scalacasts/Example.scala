package com.gilesc.scalacasts

import com.gilesc.scalacasts.dataaccess.{MySqlDatabaseDriver, UserRepository}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

object Example extends App {
  val repo = new UserRepository(MySqlDatabaseDriver)
  val username = "craiggiles04"
  val email = "myemail04"
  val myresult = for {
    insertResult <- repo.insert(username, email, "mypasswordhash")
  } yield insertResult

  println("FOUND: " + Await.result(myresult, 10 seconds))

}
