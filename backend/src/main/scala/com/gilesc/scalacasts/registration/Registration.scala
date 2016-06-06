package com.gilesc.scalacasts.registration

import com.gilesc.scalacasts.dataaccess.{MySqlDatabaseDriver, UserRepository}
import slick.driver.JdbcProfile

import scala.concurrent.Future

case class UserId(underlying: Long) extends AnyVal

class Mailer {}

case class RegistrationContext(username: String, email: String, password: String, passwordConfirmation: String) {
  assert(username.length >= 5, "Username should be greater than 5 characters")
  assert(password.length >= 8, "Password should be greater than 8 characters")
  assert(password == passwordConfirmation, "Password and Confirmation don't match")
}

class Registration {
  val repo = new UserRepository(MySqlDatabaseDriver)

  def register(mailer: Mailer)(cxt: RegistrationContext): Future[UserId] = {
    // try to validate the registration information
    // if validated:
    //  save the user information

    repo.insert(cxt.username, cxt.email, cxt.password)
    Future.successful(UserId(1L))
  }

}

