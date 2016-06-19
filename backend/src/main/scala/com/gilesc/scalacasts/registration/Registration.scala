package com.gilesc.scalacasts.registration

import cats.data.{Reader, Xor}
import com.gilesc.scalacasts.dataaccess.repository.UserRepo
import com.gilesc.scalacasts.model.{User, Email, RawPassword, Username}
import com.gilesc.security.password.PasswordHashing

import scala.concurrent.Future

trait RegistrationRepositories {
  val user: UserRepo
}

trait Registration extends PasswordHashing {
  case class RegistrationContext(username: String, email: String, password: String)
  case class RegistrationError(message: String) extends Exception(message)

  val register: RegistrationContext => Reader[RegistrationRepositories, Future[User]] = {
    cxt =>
      Reader((repos: RegistrationRepositories) => {
        def strToRegistrationError(value: String): RegistrationError = RegistrationError(value)

        val user = for {
          username <- Username(cxt.username)
          email <- Email(cxt.email)
          rawPassword <- RawPassword(cxt.password)
        } yield repos.user.insert(username, email, rawPassword)

        user.toEither match {
          case Left(error) => Future.failed(strToRegistrationError(error))
          case Right(userFuture) => userFuture
        }
      })
  }
}
