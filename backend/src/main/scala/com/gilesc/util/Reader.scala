package com.gilesc.util

import scala.concurrent.{ExecutionContext, Future}

object ReaderMonad {
  /**
    * A monad to abstract dependencies in the code, see https://coderwall.com/p/kh_z5g
    */
  object Reader {

    /**
      * an implicit to convert a function A => B in a Reader[A, B]
      */
    implicit def reader[C, R](block: C => R): Reader[C, R] = Reader(block)

    /**
      * create a reader from an already resolved result
      */
    def pure[C, A](a: A) = Reader((c: C) => a)

    /**
      * transform a sequence of Reader in a Reader of a sequence. I.E. move the Reader out of each item and keep it over the list.
      */
    def sequence[C, R](list: TraversableOnce[Reader[C, R]]): Reader[C, TraversableOnce[R]] = reader { conn =>
      for { r <- list } yield r(conn)
    }

    /**
      * provide a connection to read the content of a reader
      */
    def withConnection[C, R](connection: C)(reader: Reader[C, R]): R = reader(connection)

  }

  /**
    * the Reader Monad in itself. run is the wrapped function. C is the argument type, A is the return type.
    * C is contravariant, so that we can have:
    *
    *    trait Connection
    *    class RealConnection extends Connection
    *
    *    and have Reader[Connection, _] as a subtype of Reader[RealConnection, _] so that the we can use a RealConnection to run a Reader[Connection,_]
    */
  case class Reader[-C, +A](run: C => A) {
    /**
      * shortcut to apply the wrapped function
      */
    def apply(c: C) = run(c)

    /**
      * map a reader's return type to a new return type
      */
    def map[B](f: A => B): Reader[C, B] =
      Reader(c => f(run(c)))

    /**
      * flatMap to merge two readers. The type of connection of the second reader has to be a subtype of this reader's connection
      * to be able to use the same connection on both readers.
      */
    def flatMap[B, D <: C](f: A => Reader[D, B]): Reader[D, B] =
      Reader(c => f(run(c))(c))

    /**
      * Combine two readers
      */
    def zip[B, D <: C](other: Reader[D, B]): Reader[D, (A, B)] =
      this.flatMap { a =>
        other.map { b => (a, b) }
      }
  }

  object FutureReader {

    /**
      * transforms a Future[Reader[A, B]] in a Reader[A, Future[B]]
      */
    implicit def moveFuture[A, B](future: Future[Reader[A, B]])(implicit context: ExecutionContext): Reader[A, Future[B]] = (conn: A) => {
      for (reader <- future) yield reader(conn)
    }

    implicit def moveFutureFuture[A, B](future: Future[Reader[A, Future[B]]])(implicit context: ExecutionContext): Reader[A, Future[B]] = {
      val future1 = moveFuture(future)
      future1.map(f => f.flatMap(inf => inf))
    }

    def pure[A, B](value: B) = Reader.pure[A, Future[B]](Future.successful(value))

    /**
      * some tools for the common case of a Reader[_, Future[_]], where we want to work on what's in the future and avoid doing flatMap { _.map {}} every time
      */
    implicit class ReaderFuture[-C, +A](val reader: Reader[C, Future[A]]) {

      /**
        * shortcut for flatMap{ _.map {...} } when the inner block returns a Reader[_, Future[_]] (we also move the future around
        */
      def flatMapMapF[B, D <: C](f: A => Reader[D, Future[B]])(implicit context: ExecutionContext): Reader[D, Future[B]] = reader.flatMap { future => future.map(f) }
      /**
        * shortcut for flatMap{ _.map {...} } when the inner block returns a normal result
        */
      def flatMapMap[B, D <: C](f: A => Reader[D, B])(implicit context: ExecutionContext): Reader[D, Future[B]] = reader.flatMap { future => future.map(f) }

      def mapMap[B](f: A => B)(implicit context: ExecutionContext): Reader[C, Future[B]] = reader.map { future => future.map(f) }

    }

  }
}
