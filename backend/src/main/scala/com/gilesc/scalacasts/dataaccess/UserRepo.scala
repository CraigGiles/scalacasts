package com.gilesc.scalacasts.dataaccess

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

