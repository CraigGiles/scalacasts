package com.gilesc.scalacasts

import com.gilesc.scalacasts.dataaccess.MySqlDatabaseDriver
import com.gilesc.scalacasts.dataaccess.repository.UserRepository

import scala.language.postfixOps

object Example extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

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

