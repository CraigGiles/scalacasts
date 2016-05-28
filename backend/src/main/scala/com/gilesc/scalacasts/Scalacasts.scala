package com.gilesc.scalacasts

import com.gilesc.scalacasts.screencast.ScreencastContext
import java.time.LocalTime

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

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
    val results = for {
      screencast <- screencasts
      tag <- tags
      if (screencast.tags.contains(tag))
    } yield screencast

    ScreencastResults(results.toSet.toSeq)
  }
}
