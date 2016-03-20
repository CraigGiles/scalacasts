package com.gilesc.scalacasts.bootstrap

import akka.actor.ActorSystem
import com.gilesc.scalacasts.service.Receptionist

trait ScalacastActors {
  val system: ActorSystem

  val scalacastReceptionist =
    system.actorOf(Receptionist.props(), Receptionist.name)
}
