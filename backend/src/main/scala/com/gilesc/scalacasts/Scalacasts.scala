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

  var screencasts = Set.empty[Screencast]

  def add(cxt: ScreencastContext): Future[Int] = Future {
    screencasts = screencasts + Screencast(cxt)

    1
  }

  def findByTitle(title: Title): Future[List[Screencast]] = Future {
    screencasts.filter(_.title == title).toList
  }

  def findByTags(tags: Set[Tag]): Future[List[Screencast]] = Future {
    val results = for {
      screencast <- screencasts
      tag <- tags
      if (screencast.tags.contains(tag))
    } yield screencast

    results.toList
  }
}
