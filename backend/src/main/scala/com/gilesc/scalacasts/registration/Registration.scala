package com.gilesc.scalacasts.registration

import cats.data.Reader
import com.gilesc.scalacasts.User
import com.gilesc.scalacasts.model.{Email, RawPassword, Username}
import com.gilesc.security.password.PasswordHashing
import org.mindrot.jbcrypt.BCrypt
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

trait UserRepo {
  def insert(username: Username, email: Email, rawPassword: RawPassword): Future[User]
  def find(username: Username): Future[Option[User]]
}

object InMemoryUserRepo {
  var users = List.empty[User]
}

class InMemoryUserRepo extends UserRepo with PasswordHashing {
  import InMemoryUserRepo._
  override def insert(username: Username, email: Email, rawPassword: RawPassword): Future[User] = {
    val usr = User(users.size + 1, username, email, hash(BCrypt.gensalt())(rawPassword))
    users = usr :: users

    Future(usr)
  }

  override def find(username: Username): Future[Option[User]] = Future(users.find(_.username == username))
}

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

  //  val register: RegistrationContext => RegistrationRepositories => Xor[RegistrationError, User] = {
  //    cxt: RegistrationContext =>
  //    repos: RegistrationRepositories =>
  //
  //
  //    def strToRegistrationError(value: String): RegistrationError = RegistrationError(List[String](value))
  //    val hashWithSalt = hash(BCrypt.gensalt())
  //
  //    val user = for {
  //      username <- Username(cxt.username)
  //      email <- Email(cxt.email)
  //      rawPassword <- RawPassword(cxt.password)
  //    } yield User(0, username, email, hashWithSalt(rawPassword))
  //
  //    user.leftMap(strToRegistrationError)
  //  }
}
