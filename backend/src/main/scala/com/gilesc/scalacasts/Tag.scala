package com.gilesc.scalacasts

case class Tag(underlying: String) extends AnyVal {
  override def toString: String = underlying
}
