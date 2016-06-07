package com.gilesc.scalacasts

import com.gilesc.scalacasts.dataaccess.{MySqlDatabaseDriver, UserRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

object Example extends App {
  val repo = new UserRepository(MySqlDatabaseDriver)
  val number = "05"
  val username = s"craiggiles-$number"
  val email = s"myemail-$number"
  val password = "mypassword"

  val myresult = for {
    insertResult <- repo.insert(username, email, password)
  } yield insertResult

  //  val hashed = PasswordHasher.hash(password)
  //  println("Hashed password: " + hashed)
  //  val verified = PasswordHasher.verify(password, hashed)
  //  println("verified: " + verified)

  //  println("FOUND: " + Await.result(myresult, 10 seconds))

}

