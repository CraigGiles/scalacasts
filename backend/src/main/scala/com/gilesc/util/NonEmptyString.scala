package com.gilesc.util

import cats.data.Xor

object NonEmptyString {
  implicit val str2NonEmptyStr: String => NonEmptyString = str => new NonEmptyString(str)

  def apply(value: String): Xor[IllegalArgumentException, NonEmptyString] = {
    value.isEmpty match {
      case true => Xor.left(new IllegalArgumentException("String must not be empty"))
      case false => Xor.right(new NonEmptyString(value))
    }
  }
}

class NonEmptyString private (value: String) {
  override def toString: String = value
}

