package com.gilesc.scalacasts.registration

import cats.data.Reader
import com.gilesc.scalacasts.DatabaseBootstrap
import com.gilesc.scalacasts.dataaccess.repository.UserRepository
import com.gilesc.scalacasts.model.{Email, RawPassword, User, Username}

import scala.concurrent.Future

trait RegistrationRepositories {
  val user: UserRepository
}

trait Registration extends DatabaseBootstrap {
  case class RegistrationContext(username: String, email: String, password: String)
  case class RegistrationError(message: String) extends Exception(message)

  val register: RegistrationContext => Reader[RegistrationRepositories, Future[User]] = { cxt =>
    Reader((repos: RegistrationRepositories) => {
      def strToRegistrationError(value: String): RegistrationError = RegistrationError(value)

      val insertFunction = for {
        username <- Username(cxt.username)
        email <- Email(cxt.email)
        rawPassword <- RawPassword(cxt.password)
      } yield repos.user.insert(username, email, rawPassword)

      insertFunction.toEither match {
        case Left(error) => Future.failed(strToRegistrationError(error))
        case Right(funct) => funct(db)
      }
    })
  }
}
