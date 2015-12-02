package com.gilesc.scalacasts

import java.time.LocalTime

import akka.actor.Props
import com.gilesc.commons.akka.BaseActor
import com.gilesc.scalacasts.bootstrap.AkkaTimeoutSettings

object Receptionist {
  val name: String = "scalacasts-receptionist"

  case class AddNewScreencast(path: String, title: String, description: String, tags: String)
  case class RemoveScreencast(id: Long)

  case class FindVideoById(id: Long)
  case class FindVideoByTitle(title: Title)

  def props(): Props = Props(new Receptionist)
}

class Receptionist extends BaseActor with AkkaTimeoutSettings {
  import Receptionist._

  override def receive: Receive = {
    case AddNewScreencast(path, title, desc, tags) => addNewScreencast(path, title, desc, tags)
    case RemoveScreencast(id) => removeScreencast(id)
    case FindVideoById(id) => findById(id)
    case FindVideoByTitle(title) => findByTitle(title)
  }

  def addNewScreencast(path: String, title: String, description: String, tags: String): Unit = {
    val screencast = Screencast(path, title, description, tags)
    log.info("Adding new screencast: {}", screencast)
    // TODO: add screencast to library actor
  }

  def removeScreencast(id: Long): Unit = {
    log.info("Removing screencast {}", id)
    // TODO: remove screencast `id from Library actor
  }

  def findById(id: Long): Unit = {
    log.info("Finding by ID: {}", id)
    // TODO: Get the screencast at ID for caller
  }

  def findByTitle(title: Title): Unit = {
    log.info("Finding by TITLE: {}", title)
    // TODO: Get screencast with matching title for caller
  }
}
