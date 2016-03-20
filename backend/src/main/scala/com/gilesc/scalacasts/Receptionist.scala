package com.gilesc.scalacasts

import akka.actor.{ActorRef, Props}
import akka.pattern.{ask, pipe}
import com.gilesc.commons.akka.BaseActor
import com.gilesc.scalacasts.bootstrap.AkkaTimeoutSettings
import com.gilesc.scalacasts.screencast.ScreencastContext

object Receptionist {
  val name: String = "scalacasts-receptionist"

  case class AddNewScreencast(cxt: ScreencastContext)

  case class FindByTitle(title: Title)
  case class FindByTags(tags: Set[Tag])
  case class ScreencastResults(screencasts: Seq[Screencast])

  case class Successful(isSuccessful: Boolean)

  def props(): Props = Props(new Receptionist)
}

class Receptionist extends BaseActor with AkkaTimeoutSettings {
  import Receptionist._

  var screencasts = Seq.empty[Screencast]
  // val library = context.actorOf(Library.props(), Library.name)

  override def receive: Receive = {
    case AddNewScreencast(cxt: ScreencastContext) => addScreencast(cxt)
    case FindByTitle(title) => findByTitle(title)
    case FindByTags(tags) => findByTags(tags)
  }

  def addScreencast(cxt: ScreencastContext): Unit = {
    screencasts = screencasts :+ Screencast(cxt)
    sender() ! Successful(true)
  }

  def findByTitle(title: Title): Unit = {
    sender() ! ScreencastResults(screencasts.filter(_.title == title))
  }

  def findByTags(tags: Set[Tag]): Unit = {
    var results = Seq.empty[Screencast]

    screencasts.foreach { screencast =>
      tags.foreach { tag =>
        if (screencast.tags.contains(tag) && !results.contains(screencast))
          results = results :+ screencast
      }
    }

    sender() ! ScreencastResults(results)
  }
}
