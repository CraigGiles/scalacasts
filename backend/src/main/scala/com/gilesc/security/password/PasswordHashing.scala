package com.gilesc.security.password

trait PasswordHashing {
  def hash(password: String): HashedPassword
  def verify(password: String, hashed: HashedPassword): Boolean
}
