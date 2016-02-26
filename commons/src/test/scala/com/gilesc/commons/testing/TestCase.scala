package com.gilesc.commons.testing

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.testkit.{TestKit, TestActorRef}
import org.scalatest._
import org.scalatest.Matchers._

import Matchers._

abstract class TestCase extends TestKit(ActorSystem("testsystem"))
    with WordSpecLike with Matchers {

      //TODO: change "testsystem" to config

}
