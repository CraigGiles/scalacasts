package com.gilesc.scalacasts

import akka.actor.{ActorRef, Props}
import akka.pattern.{ask, pipe}
import com.gilesc.commons.akka.BaseActor
import com.gilesc.scalacasts.bootstrap.AkkaTimeoutSettings
import java.time.LocalTime
import com.gilesc.scalacasts.screencast.ScreencastContext

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Scalacasts {
  case class ScreencastResults(screencasts: Seq[Screencast])
}

class Scalacasts() {
  import Scalacasts._

  var screencasts = Seq.empty[Screencast]

  def add(cxt: ScreencastContext): Future[Int] = Future {
    screencasts = screencasts :+ Screencast(cxt)

    1
  }

  def findByTitle(title: Title): Future[ScreencastResults] = Future {
    ScreencastResults(screencasts.filter(_.title == title))
  }

  def findByTags(tags: Set[Tag]): Future[ScreencastResults] = Future {
    var results = Seq.empty[Screencast]

    screencasts.foreach { screencast =>
      tags.foreach { tag =>
        if (screencast.tags.contains(tag) && !results.contains(screencast))
          results = results :+ screencast
      }
    }

    ScreencastResults(results)
  }
}
