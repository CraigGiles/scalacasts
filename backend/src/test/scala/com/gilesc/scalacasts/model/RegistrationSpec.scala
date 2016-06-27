package com.gilesc.scalacasts.model

import com.gilesc.scalacasts.DatabaseBootstrap
import com.gilesc.scalacasts.dataaccess.repository.UserRepository
import com.gilesc.scalacasts.registration.{Registration, RegistrationRepositories}
import com.gilesc.scalacasts.testing.TestCase
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.ExecutionContext.Implicits.global

class RegistrationSpec extends TestCase with ScalaFutures with Registration with DatabaseBootstrap {

  "Registration System" should {
    "give back a user object after proper information is provided" in {
      val cxt = RegistrationContext("username", "email@test.com", "rawpassword")
      val repos = new RegistrationRepositories {
        override val user = new UserRepository
      }

      //      val usr = for {
      //        user <- register(cxt)(repos)
      //      } yield user
      //
      //      whenReady(usr) { user =>
      //        user.email.value should be(cxt.email)
      //        user.username.value should be(cxt.username)
      //        user.passwordHash.password should not be cxt.password
      //      }
    }
  }

}
