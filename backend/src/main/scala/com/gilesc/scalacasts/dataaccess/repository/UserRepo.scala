package com.gilesc.scalacasts.dataaccess.repository

import com.gilesc.scalacasts.User
import com.gilesc.scalacasts.model.{Email, RawPassword, Username}

import scala.concurrent.Future

trait UserRepo {
  def insert(username: Username, email: Email, rawPassword: RawPassword): Future[User]
  def find(username: Username): Future[Option[User]]
}

