package com.gilesc.scalacasts.model

import com.gilesc.scalacasts.registration.Registration
import com.gilesc.scalacasts.testing.TestCase

class RegistrationSpec extends TestCase with Registration {

  "Registration System" should {
    "give back a user object after proper information is provided" in {
      val cxt = RegistrationContext("username", "email@test.com", "rawpassword")
      val user = register(cxt)

      user.isRight should be(true)
      user.toOption.foreach { u =>
        u.email.value should be(cxt.email)
        u.username.value should be(cxt.username)
        u.passwordHash.password should not be cxt.password
      }
    }
  }

}
