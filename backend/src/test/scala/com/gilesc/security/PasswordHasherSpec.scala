package com.gilesc.security

import com.gilesc.scalacasts.testing.TestCase
import com.gilesc.security.password.{PasswordHasher, Bcrypt}
import com.gilesc.security.password.scheme.BcryptPasswordHasher

class PasswordHasherSpec extends TestCase {
  "Password Hashing" should {
    val hasher = PasswordHasher(Bcrypt)
    val password = "mypassword"

    "create a bcrypt hashing scheme" in {
      val hasher = PasswordHasher(Bcrypt)
      hasher.isInstanceOf[BcryptPasswordHasher] should be(true)
    }

    "hash a password and return a HashedPassword with salt" in {
      val result = hasher.hash(password)
      result.password should not be password
      result.salt.isDefined should be(true)
      result.salt.get should not be password
    }

    "verify a password matches its hash" in {
      val hashed = hasher.hash(password)
      val result = hasher.verify(password, hashed)
      result should be(true)
    }
  }
}
