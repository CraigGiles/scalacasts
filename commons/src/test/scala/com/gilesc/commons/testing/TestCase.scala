package com.gilesc.commons.testing

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.testkit.{TestKit, TestActorRef}
import org.scalatest._
import org.scalatest.Matchers._
import org.scalatest.concurrent._

import Matchers._

abstract class TestCase extends WordSpecLike
    with Matchers
    with GivenWhenThen
    with ScalaFutures {
}
