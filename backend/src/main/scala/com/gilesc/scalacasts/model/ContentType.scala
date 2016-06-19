package com.gilesc.scalacasts.model

import com.gilesc.util.NonEmptyString

object ContentType {
  def apply(value: String) = NonEmptyString(value) map (new ContentType(_))
}

case class ContentType(value: NonEmptyString)
