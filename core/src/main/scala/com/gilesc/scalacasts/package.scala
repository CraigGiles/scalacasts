package com.gilesc

import scala.language.implicitConversions

package object scalacasts {
  implicit def stringToTitle(value: String): Title = Title(value)
  implicit def stringToTag(value: String): Tag = Tag(value)
  implicit def stringToDescription(value: String): Description = Description(value)
  implicit def stringToContentType(value: String): ContentType = ContentType(value)
}
