package com.gilesc.scalacasts.model

import com.gilesc.scalacasts.registration.{InMemoryUserRepo, Registration, RegistrationRepositories}
import com.gilesc.scalacasts.testing.TestCase
import org.scalatest.concurrent.ScalaFutures

class RegistrationSpec extends TestCase with ScalaFutures with Registration {

  "Registration System" should {
    "give back a user object after proper information is provided" in {
      val cxt = RegistrationContext("username", "email@test.com", "rawpassword")
      val repos = new RegistrationRepositories {
        override val user = new InMemoryUserRepo
      }

      val usr = for {
        user <- register(cxt)(repos)
      } yield user
      //      val user = register(cxt)
      //
      usr.isRight should be(true)
      usr map { u =>
        whenReady(u) { user =>
          user.email.value should be(cxt.email)
          user.username.value should be(cxt.username)
          user.passwordHash.password should not be cxt.password
        }

      }
      //      whenReady(usr) { user =>
      //        user.toOption.foreach { u =>
      //          u.email.value should be(cxt.email)
      //          u.username.value should be(cxt.username)
      //          u.passwordHash.password should not be cxt.password
      //        }
      //      }
    }
  }

}
