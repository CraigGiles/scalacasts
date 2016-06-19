package com.gilesc.scalacasts.model

import cats.Functor
import cats.data.{Xor, OneAnd}
import com.gilesc.util.NonEmptyString

/**
  * scala> def add1(n:Int) = n + 1
  * add1: (n: Int)Int
  *
  * scala> Some(1).map(add1) // ok
  * res0: Option[Int] = Some(2)
  *
  * scala> List(1,2,3).map(add1) // ok
  * res1: List[Int] = List(2, 3, 4)
  *
  *
  *
  * case class Box2[A](fst: A, snd: A)
  * implicit val boxFunctor = new Functor[Box2] {
  * |   def map[A, B](fa: Box2[A])(f: A => B): Box2[B] = Box2(f(fa.fst), f(fa.snd))
  * | }
  */

trait NonEmpty[T] {
  def apply(value: T)(f: (T) => Boolean): Xor[IllegalArgumentException, T] =
    if (f(value)) Xor.right(value)
    else Xor.left(new IllegalArgumentException("Value can not be created"))
}

object Title {
  def apply(value: String) = NonEmptyString(value) map (x => new Title(x.toString))
}

class Title private (val value: String) {
  override val toString = value
}
