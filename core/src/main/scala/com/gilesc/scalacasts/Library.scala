package com.gilesc.scalacasts

import akka.actor.{ActorRef, Props}
import com.gilesc.commons.akka.BaseActor

object Library {
  val name: String = "scalacasts-library"

  case class AddScreencast(screencast: Screencast)
  case class RemoveScreencast(title: Title)

  case class FindByTitle(name: Title)
  case class ScreencastResults(screencasts: Set[Screencast])

  case object AllScreencasts

  def props(): Props = Props(new Library)
}

/**
  * Library is a host for all videos on the site
  */
class Library extends BaseActor {
  import Library._

  var screencasts: Set[Screencast] = Set.empty[Screencast]

  override def receive: Receive = {
    case Receptionist.RequestContext(request, replyTo) =>
      request match {
        case AddScreencast(screencast) => add(screencast, replyTo)
        case RemoveScreencast(title) => remove(title, replyTo)
        case FindByTitle(title) =>
          val cast = screencasts.filter(_.title == title)
        case AllScreencasts => sender() ! ScreencastResults(screencasts)
      }
  }

  def add(screencast: Screencast, replyTo: ActorRef): Unit = {
    log.info("Adding Screencast to library: {}", screencast.title)
    screencasts = screencasts + screencast

    replyTo ! Receptionist.Successful(true)
  }

  def remove(title: Title, replyTo: ActorRef): Unit = {
    log.info("Removing Screencast from library: {}", title)
    screencasts = screencasts.filterNot(_.title == title)

    replyTo ! Receptionist.Successful(true)
  }
}
