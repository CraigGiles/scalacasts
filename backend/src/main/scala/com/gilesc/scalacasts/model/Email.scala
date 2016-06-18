package com.gilesc.scalacasts.model

import cats.data.Xor

object Email {
  def apply(email: String) = {
    val error = s"Email $email doesn't conform to proper email standards"
    val regex = "\\b[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\b".r

    if (regex.pattern.matcher(email).matches)
      Xor.right(new Email(email)) else Xor.left(error)
  }
}

class Email private (val value: String)
