package com.gilesc.scalacasts.registration

import cats.data.Xor
import com.gilesc.scalacasts.User
import com.gilesc.scalacasts.model.{Email, RawPassword, Username}
import com.gilesc.security.password.PasswordHashing
import org.mindrot.jbcrypt.BCrypt

trait Registration extends PasswordHashing {
  case class RegistrationContext(username: String, email: String, password: String)
  case class RegistrationError(messages: List[String])

  val register: RegistrationContext => Xor[RegistrationError, User] = { cxt =>
    def strToRegistrationError(value: String): RegistrationError = RegistrationError(List[String](value))
    val hashWithSalt = hash(BCrypt.gensalt())

    val user = for {
      username <- Username(cxt.username)
      email <- Email(cxt.email)
      rawPassword <- RawPassword(cxt.password)
    } yield User(0, username, email, hashWithSalt(rawPassword))

    user.leftMap(strToRegistrationError)
  }
}
