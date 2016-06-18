package com.gilesc.scalacasts.model

import com.gilesc.scalacasts.testing.TestCase

class RawPasswordSpec extends TestCase {
  "Validating password" should {
    "return a valid RawPassword if correct" in {
      val password = "mypasswordishere"

      val pw = RawPassword(password)
      pw.isRight should be(true)
      pw.toOption.foreach(p => p.value should be(password))
    }
  }
}
