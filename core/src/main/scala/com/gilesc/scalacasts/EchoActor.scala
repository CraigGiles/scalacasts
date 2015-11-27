package com.gilesc.scalacasts

import akka.actor.Actor.Receive
import akka.actor.{ActorLogging, Actor, Props}

object EchoActor {
  def name = "echo-actor"

  def props(): Props = Props(new EchoActor)
}

class EchoActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case x: String => log.info(x)
      sender() ! x + "back atcha"
  }
}
