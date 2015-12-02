package com.gilesc.scalacasts

import akka.actor.{ActorRef, Props}
import com.gilesc.commons.akka.BaseActor

object Library {
  val name: String = "scalacasts-library"

  case class AddScreencast(screencast: Screencast)
  case object AllScreencasts
  case class FindByTitle(name: Title)

  case class ScreencastResults(screencasts: Set[Screencast])

  def props(): Props = Props(new Library)
}

/**
  * Library is a host for all videos on the site
  */
class Library extends BaseActor {
  import Library._

  var screencasts: Set[Screencast] = Set.empty[Screencast]

  override def receive: Receive = {
    case Receptionist.RequestContext(AddScreencast(screencast), replyTo) => addScreencast(screencast, replyTo)
    case FindByTitle(title) =>
      val cast = screencasts.filter(_.title == title)
    case AllScreencasts => sender() ! ScreencastResults(screencasts)
  }

  def addScreencast(screencast: Screencast, replyTo: ActorRef): Unit = {
    log.info("SUCCESS - Add Screencast: {}, replyTo: {}", screencast, replyTo.path)
    screencasts = screencasts + screencast
    replyTo ! Receptionist.Successful(true)
  }
}
