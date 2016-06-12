package com.gilesc.security.password

case class HashedPassword(password: String, salt: Option[String])
