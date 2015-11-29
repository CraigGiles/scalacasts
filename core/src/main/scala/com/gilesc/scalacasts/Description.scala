package com.gilesc.scalacasts

case class Description(underlying: String) extends AnyVal {
  override def toString: String = underlying
}

