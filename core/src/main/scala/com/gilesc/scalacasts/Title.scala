package com.gilesc.scalacasts

case class Title(underlying: String) extends AnyVal {
  override def toString: String = underlying
}
