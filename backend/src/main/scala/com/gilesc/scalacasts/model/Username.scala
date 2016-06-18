package com.gilesc.scalacasts.model

import cats.data.Xor

object Username {
  def apply(username: String) = {
    val error = "Username must be between 5 and 40 characters"
    if (username.length < 5 || username.length > 40) Xor.left(error)
    else Xor.right(new Username(username))
  }
}

class Username private (val value: String)

