package com.gilesc.scalacasts

import akka.actor.{ActorRef, Props}
import akka.pattern.{ask, pipe}
import com.gilesc.commons.akka.BaseActor
import com.gilesc.scalacasts.bootstrap.AkkaTimeoutSettings
import java.time.LocalTime
import com.gilesc.scalacasts.screencast.ScreencastContext

object Scalacasts {
  val name = "scalacasts-receptionist"

  case class AddNewScreencast(cxt: ScreencastContext)
  case class FindByTitle(title: Title)

  def props(): Props = Props(new Scalacasts)
}

class Scalacasts extends BaseActor with AkkaTimeoutSettings {
  import Scalacasts._

  var screencasts = Seq.empty[Screencast]

  def receive: Receive = {
    case AddNewScreencast(cxt: ScreencastContext) => addScreencast(cxt)
    case FindByTitle(title) => findByTitle(title)
  }

  /**
    * Constructs and adds a Screencast object to the library
    * @param cxt ScreencastContext
    */
  def addScreencast(cxt: ScreencastContext): Unit = {
    screencasts = screencasts :+ Screencast(cxt)
  }

  /**
    * Returns a Seq of Screencast objects which match the given title
    * @param title Title
    */
  def findByTitle(title: Title): Unit = {
    sender() ! screencasts.filter(_.title == title)
  }
}