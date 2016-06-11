package com.gilesc.scalacasts.registration

import com.gilesc.scalacasts.User
import com.gilesc.scalacasts.dataaccess.MySqlDatabaseDriver
import com.gilesc.scalacasts.dataaccess.repository.UserRepository

import scala.concurrent.Future

case class UserId(underlying: Long) extends AnyVal

trait UserMailer {
  def welcome(user: User): Boolean
}

case class RegistrationContext(username: String, email: String, password: String, passwordConfirmation: String) {
  assert(username.length >= 5, "Username should be greater than 5 characters")
  assert(password.length >= 8, "Password should be greater than 8 characters")
  assert(password == passwordConfirmation, "Password and Confirmation don't match")
}

class Registration {
  val repo = new UserRepository(MySqlDatabaseDriver)

  def register(mailer: UserMailer)(cxt: RegistrationContext): Future[User] = {
    for {
      user <- repo.insert(cxt.username, cxt.email, cxt.password)
      sent <- mailer.welcome(user)
    } yield user
  }

}

