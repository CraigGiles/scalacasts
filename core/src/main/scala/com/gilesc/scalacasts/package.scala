package com.gilesc

package object scalacasts {
  implicit def stringToTitle(value: String): Title = Title(value)
  implicit def stringToTag(value: String): Tag = Tag(value)
}
