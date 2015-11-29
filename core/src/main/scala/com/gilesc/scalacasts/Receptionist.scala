package com.gilesc.scalacasts

import java.time.LocalTime

import akka.actor.Props
import com.gilesc.commons.akka.BaseActor
import com.gilesc.scalacasts.bootstrap.AkkaTimeoutSettings

object Receptionist {
  val name: String = "scalacasts-receptionist"

  case class FindVideoById(id: Long)
  case class FindVideoByTitle(title: Title)

  def props(): Props = Props(new Receptionist)
}

class Receptionist extends BaseActor with AkkaTimeoutSettings {
  val lib = context.system.actorOf(Library.props(), Library.name)
  import Receptionist._

  override def receive: Receive = {
    case FindVideoById(id) => findById(id)
    case FindVideoByTitle(title) => findByTitle(title)
  }

  def findById(id: Long): Unit = {
    import akka.pattern.ask
    val results = lib ? Library.AllLessons
    sender ! results
      //Video(id, "random title goes here", Set[Tag]("Some", "thing", "wicked"), LocalTime.now(), LocalTime.now())
  }

  def findByTitle(title: Title): Unit = {
//    sender() ! Video(1, title, Set[Tag]("Some", "time"), LocalTime.now(), LocalTime.now())
  }
}
