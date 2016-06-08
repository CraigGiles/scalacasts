package com.gilesc.security.password

import org.mindrot.jbcrypt.BCrypt

trait PasswordHashing {
  def hash(password: String): HashedPassword = {
    val logRounds = 20000
    val salt = BCrypt.gensalt()
    val hashedPassword = BCrypt.hashpw(password, salt)

    HashedPassword(hashedPassword, Option(salt))
  }

  def verify(password: String, hashed: HashedPassword): Boolean =
    BCrypt.checkpw(password, hashed.password)
}
