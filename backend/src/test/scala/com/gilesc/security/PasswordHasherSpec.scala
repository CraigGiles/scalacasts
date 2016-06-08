package com.gilesc.security

import com.gilesc.scalacasts.testing.TestCase
import com.gilesc.security.password.PasswordHashing

class PasswordHasherSpec extends TestCase with PasswordHashing {
  "Password Hashing" should {
    val password = "mypassword"

    "hash a password and return a HashedPassword with salt" in {
      val result = hash(password)
      result.password should not be password
      result.salt.isDefined should be(true)
      result.salt.get should not be password
    }

    "verify a password matches its hash" in {
      val hashed = hash(password)
      val result = verify(password, hashed)
      result should be(true)
    }
  }
}
