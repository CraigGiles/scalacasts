package com.gilesc.scalacasts.testing

import org.scalatest._
import org.scalatest.concurrent._

abstract class TestCase extends WordSpecLike
    with Matchers
    with GivenWhenThen
    with ScalaFutures {
}
