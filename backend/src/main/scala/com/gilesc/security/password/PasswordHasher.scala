package com.gilesc.security.password

import com.gilesc.security.password.scheme.BcryptPasswordHasher

sealed trait HashingScheme
case object Bcrypt extends HashingScheme

object PasswordHasher {
  def apply(scheme: HashingScheme): PasswordHashing = scheme match {
    case Bcrypt => new BcryptPasswordHasher
  }
}

