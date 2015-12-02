package com.gilesc.scalacasts

import akka.actor.{ActorRef, Props}
import akka.pattern.{ask, pipe}
import com.gilesc.commons.akka.BaseActor
import com.gilesc.scalacasts.bootstrap.AkkaTimeoutSettings

object Receptionist {
  val name: String = "scalacasts-receptionist"

  case class RequestContext(request: Any, replyTo: ActorRef)

  case class AddNewScreencast(path: String, title: String, description: String, tags: String)
  case class RemoveScreencast(id: Long)

  case class FindVideoById(id: Long)
  case class FindVideoByTitle(title: Title)

  case class Successful(boolean: Boolean)

  def props(): Props = Props(new Receptionist)
}

class Receptionist extends BaseActor with AkkaTimeoutSettings {
  import Receptionist._

  val library = context.actorOf(Library.props(), Library.name)

  override def receive: Receive = {
    case AddNewScreencast(path, title, desc, tags) => addNewScreencast(path, title, desc, tags)
    case RemoveScreencast(id) => removeScreencast(id)
    case FindVideoById(id) => findById(id)

    case FindVideoByTitle(title) => findByTitle(title)
    case Library.ScreencastResults(screencasts) =>
  }

  def addNewScreencast(path: String, title: String, description: String, tags: String): Unit = {
    val screencast = Screencast(path, title, description, tags)
    log.info("Adding new screencast: {}", screencast)

    library ! RequestContext(Library.AddScreencast(screencast), sender())
  }

  def removeScreencast(title: String): Unit = {
    log.info("Removing screencast {}", title)

    library ! RequestContext(Library.RemoveScreencast(title), sender())
  }

  def findById(id: Long): Unit = {
    log.info("Finding by ID: {}", id)
  }

  def findByTitle(title: Title): Unit = {
    import context.dispatcher

    log.info("Finding by TITLE: {}", title)
    library ? Library.FindByTitle(title) pipeTo self
  }
}
