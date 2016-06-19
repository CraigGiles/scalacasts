package com.gilesc.scalacasts.model

/**
  * Created by gilesc on 6/19/16.
  */
case class Description(underlying: String) extends AnyVal {
}

import com.gilesc.util.NonEmptyString

//object ContentType {
//  def apply(value: String) = NonEmptyString(value) map (new ContentType(_))
//}
//
//case class ContentType(value: NonEmptyString)
