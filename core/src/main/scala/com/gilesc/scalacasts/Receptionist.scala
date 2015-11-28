package com.gilesc.scalacasts

import java.time.LocalTime

import akka.actor.Props
import com.gilesc.commons.akka.BaseActor

object Receptionist {
  val name: String = "scalacasts-receptionist"

  case class FindVideoById(id: Long)
  case class FindVideoByTitle(title: Title)

  def props(): Props = Props(new Receptionist)
}

class Receptionist extends BaseActor {
  import Receptionist._

  override def receive: Receive = {
    case FindVideoById(id) => findById(id)
    case FindVideoByTitle(title) => findByTitle(title)
  }

  def findById(id: Long): Unit = {
    sender() ! Video(id, "random title goes here", Set[Tag]("Some", "thing", "wicked"), LocalTime.now(), LocalTime.now())
  }

  def findByTitle(title: Title): Unit = {
    sender() ! Video(1, title, Set[Tag]("Some", "time"), LocalTime.now(), LocalTime.now())
  }
}
