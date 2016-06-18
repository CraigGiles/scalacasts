package com.gilesc.scalacasts.model

import cats.data.Xor

object RawPassword {
  def apply(password: String) = {
    val error = "Password must be greater than 8 characters"
    if (password.length < 8) Xor.left(error)
    else Xor.right(new RawPassword(password))
  }
}

class RawPassword private (val value: String)

