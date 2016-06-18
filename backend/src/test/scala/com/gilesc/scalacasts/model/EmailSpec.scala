package com.gilesc.scalacasts.model

import com.gilesc.scalacasts.registration.Registration
import com.gilesc.scalacasts.testing.TestCase

class EmailSpec extends TestCase with Registration {
  "Validating emails" should {
    "return a valid email if validated" in {
      val email = "my@emailtest.com"

      val em = Email(email)
      em.isRight should be(true)
      em.toOption.foreach(p => p.value should be(email))
    }
  }
}
