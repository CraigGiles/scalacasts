package com.gilesc.scalacasts

case class ContentType(underlying: String) extends AnyVal {
  override def toString: String = underlying
}

