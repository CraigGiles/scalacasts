package com.gilesc.scalacasts.model

import com.gilesc.util.NonEmptyString

object Title {
  def apply(value: String) = NonEmptyString(value) map (new Title(_))
}

case class Title private (value: NonEmptyString)
