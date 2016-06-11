package com.gilesc.scalacasts.registration

import com.gilesc.scalacasts.testing.TestCase

class RegistrationSpec extends TestCase {
  val username = "mytestuser"
  val email = "mytestuser@email.com"
  val password = "test1234"

  "The registration context" should {
    "invalidate usernames less than 5 characters" in {
      intercept[AssertionError] {
        new RegistrationContext("1234", email, password, password)
      }

      new RegistrationContext("12345", email, password, password)
    }

    "invalidate passwords less than 8 characters" in {
      intercept[AssertionError] {
        new RegistrationContext(username, email, "1234567", "1234567")
      }

      new RegistrationContext(username, email, "12345678", "12345678")
    }

    "ensure passwords and passwordConfirmation match" in {
      intercept[AssertionError] {
        new RegistrationContext(username, email, password + "1", password)
      }

      new RegistrationContext(username, email, password, password)
    }
  }

  "The registration system" should {
  }
}

