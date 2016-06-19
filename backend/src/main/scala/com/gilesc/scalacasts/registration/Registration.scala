package com.gilesc.scalacasts.registration

import cats.data.{Xor, Reader}
import com.gilesc.scalacasts.User
import com.gilesc.scalacasts.dataaccess.repository.UserRepo
import com.gilesc.scalacasts.model.{Email, RawPassword, Username}
import com.gilesc.security.password.PasswordHashing

import scala.concurrent.Future

trait RegistrationRepositories {
  val user: UserRepo
}

trait Registration extends PasswordHashing {
  case class RegistrationContext(username: String, email: String, password: String)
  case class RegistrationError(messages: List[String])

  val register: RegistrationContext => Reader[RegistrationRepositories, Xor[RegistrationError, Future[User]]] = {
    cxt =>
      Reader((repos: RegistrationRepositories) => {
        def strToRegistrationError(value: String): RegistrationError = RegistrationError(List[String](value))

        val user = for {
          username <- Username(cxt.username)
          email <- Email(cxt.email)
          rawPassword <- RawPassword(cxt.password)
        } yield repos.user.insert(username, email, rawPassword)

        user.leftMap(strToRegistrationError)
      })
  }
}
