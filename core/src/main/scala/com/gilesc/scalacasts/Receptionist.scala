package com.gilesc.scalacasts

import java.time.LocalTime

import akka.actor.Props
import com.gilesc.commons.akka.BaseActor
import com.gilesc.scalacasts.bootstrap.AkkaTimeoutSettings

object Receptionist {
  val name: String = "scalacasts-receptionist"

  case class AddNewScreencast(path: String, title: String, description: String, tags: String)

  case class FindVideoById(id: Long)
  case class FindVideoByTitle(title: Title)

  def props(): Props = Props(new Receptionist)
}

class Receptionist extends BaseActor with AkkaTimeoutSettings {
  import Receptionist._

  override def receive: Receive = {
    case AddNewScreencast(path, title, desc, tags) => addNewScreencast(path, title, desc, tags)
    case FindVideoById(id) => findById(id)
    case FindVideoByTitle(title) => findByTitle(title)
  }

  def addNewScreencast(path: String, title: String, description: String, tags: String): Unit = {
    val screencast = Screencast(path, title, description, tags)
    log.info("Adding new screencast: {}", screencast)
    // TODO: add screencast to library actor
  }

  def findById(id: Long): Unit = {
    log.info("Finding by ID {}", id)
      sender ! Video(1, "title", "description", Set[Tag]("hey"), LocalTime.now(), LocalTime.now(), None)
  }

  def findByTitle(title: Title): Unit = {
  }
}
