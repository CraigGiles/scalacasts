package com.gilesc.scalacasts.registration

import com.gilesc.scalacasts.User
import com.gilesc.scalacasts.dataaccess.MySqlDatabaseDriver
import com.gilesc.scalacasts.dataaccess.repository.UserRepository

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

case class UserId(underlying: Long) extends AnyVal

abstract class Mailer {
  def send(from: String, to: String, subject: String, body: String): Boolean = true
}

class UserMailer extends Mailer {
  def welcome(user: User): Boolean = send("from", "to", "subject", "body")
}

case class RegistrationContext(username: String, email: String, password: String, passwordConfirmation: String) {
  assert(username.length >= 5, "Username should be greater than 5 characters")
  assert(password.length >= 8, "Password should be greater than 8 characters")
  assert(password == passwordConfirmation, "Password and Confirmation don't match")
}

class Registration {
  val repo = new UserRepository(MySqlDatabaseDriver)

  def register(mailer: UserMailer)(cxt: RegistrationContext): Future[User] = {
    repo.insert(cxt.username, cxt.email, cxt.password) flatMap { user =>
      mailer.welcome(user)

      Future(user)
    }
  }

}

