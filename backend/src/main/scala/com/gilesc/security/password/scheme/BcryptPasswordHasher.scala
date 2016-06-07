package com.gilesc.security.password.scheme

import com.gilesc.security.password.{HashedPassword, PasswordHashing}
import org.mindrot.jbcrypt.BCrypt

/**
  * Created by gilesc on 6/7/16.
  */
class BcryptPasswordHasher extends PasswordHashing {
  def hash(password: String): HashedPassword = {
    val logRounds = 20000
    val salt = BCrypt.gensalt()
    val hashedPassword = BCrypt.hashpw(password, salt)

    HashedPassword(hashedPassword, Option(salt))
  }

  def verify(password: String, hashed: HashedPassword): Boolean =
    BCrypt.checkpw(password, hashed.password)
}
