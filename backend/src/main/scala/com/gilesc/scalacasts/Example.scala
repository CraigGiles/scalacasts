package com.gilesc.scalacasts

import com.gilesc.scalacasts.dataaccess.MySqlDatabaseDriver
import com.gilesc.scalacasts.dataaccess.repository.UserRepository
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

object Example extends App with LazyLogging {
  import scala.concurrent.ExecutionContext.Implicits.global

  val repo = new UserRepository(MySqlDatabaseDriver)
  val number = Random.nextInt()
  val username = s"cg-$number"
  val email = s"em-$number"
  val password = "mypassword"

  val myresult = for {
    insertResult <- repo.insert(username, email, password)
  } yield insertResult

  myresult onComplete { r =>
    logger.info("RESULT: {}", r)
  }

  Await.result(myresult, 10 seconds)
}

