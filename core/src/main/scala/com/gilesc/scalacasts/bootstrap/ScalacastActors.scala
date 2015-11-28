package com.gilesc.scalacasts.bootstrap

import akka.actor.ActorSystem
import com.gilesc.scalacasts.Receptionist

/**
  * Created by gilesc on 11/28/15.
  */
trait ScalacastActors {
  val system: ActorSystem

  val scalacasts = system.actorOf(Receptionist.props(), Receptionist.name)
}
