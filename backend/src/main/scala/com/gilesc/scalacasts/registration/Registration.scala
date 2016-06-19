package com.gilesc.scalacasts.registration

import cats.data.Reader
import com.gilesc.scalacasts.dataaccess.UserRepo
import com.gilesc.scalacasts.model.{Email, RawPassword, Username}
import com.gilesc.security.password.PasswordHashing

trait RegistrationRepositories {
  val user: UserRepo
}

trait Registration extends PasswordHashing {
  case class RegistrationContext(username: String, email: String, password: String)
  case class RegistrationError(messages: List[String])

  def register(cxt: RegistrationContext) = Reader((repos: RegistrationRepositories) => {
    def strToRegistrationError(value: String): RegistrationError = RegistrationError(List[String](value))

    val user = for {
      username <- Username(cxt.username)
      email <- Email(cxt.email)
      rawPassword <- RawPassword(cxt.password)
    } yield repos.user.insert(username, email, rawPassword)

    user.leftMap(strToRegistrationError)
  })
}
