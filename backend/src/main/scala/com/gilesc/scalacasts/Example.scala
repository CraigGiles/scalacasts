package com.gilesc.scalacasts

import com.gilesc.scalacasts.dataaccess.DatabaseProfile
import com.gilesc.scalacasts.dataaccess.repository.UserRepository
import com.gilesc.scalacasts.model.{Email, RawPassword, Username}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

object Example extends App with LazyLogging {
  import scala.concurrent.ExecutionContext.Implicits.global
  val repo = new UserRepository()
  val number = Random.nextInt()
  val username = s"cg-$number"
  val email = s"em-$number"
  val password = "mypassword"

  val un = Username(username).toList.head
  val em = Email(email).toList.head
  val pw = RawPassword(password).toList.head

  val config = ConfigFactory.load()
  val db = DatabaseProfile(config)

  val myresult = for {
    insertResult <- repo.insert(un, em, pw)(db)
  } yield insertResult

  myresult onComplete { r =>
    logger.info("RESULT: {}", r)
  }

  Await.result(myresult, 10 seconds)
}

