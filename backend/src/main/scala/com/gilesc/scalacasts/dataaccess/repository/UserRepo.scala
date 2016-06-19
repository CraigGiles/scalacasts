package com.gilesc.scalacasts.dataaccess.repository

import com.gilesc.scalacasts.model.{User, Email, RawPassword, Username}

import scala.concurrent.Future

trait UserRepo {
  def insert(username: Username, email: Email, rawPassword: RawPassword): Future[User]
  def find(username: Username): Future[Option[User]]
  def find(email: Email): Future[Option[User]]
}

