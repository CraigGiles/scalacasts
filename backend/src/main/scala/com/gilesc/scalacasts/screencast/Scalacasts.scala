package com.gilesc.scalacasts.screencast

import com.gilesc.scalacasts.model.{Tag, Title}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Scalacasts() {
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
