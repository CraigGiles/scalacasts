package com.gilesc.scalacasts

import java.time.LocalTime

import akka.actor.Props
import com.gilesc.commons.akka.BaseActor

object Library {
  val name: String = "scalacasts-library"

  case class AddLesson(title: Title, description: Description, tags: Set[Tag])
  case object AllLessons

  case class LessonResults(videos: Set[Video])

  def props(): Props = Props(new Library)
}

/**
  * Library is a host for all videos on the site
  */
class Library extends BaseActor {
  import Library._

  var videos: Set[Video] = Set.empty[Video]

  override def receive: Receive = {
    case AddLesson(title, desc, tags) => addNewLesson(title, desc, tags)
    case AllLessons => sender() ! LessonResults(videos)
  }

  def addNewLesson(title: Title, description: Description, tags: Set[Tag]): Unit = {
    val timestamp = LocalTime.now()
    videos = videos + Video(nextId, title, description, tags, timestamp, timestamp, None)
  }

  def nextId: Long = videos.size + 1
}
