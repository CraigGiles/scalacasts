package com.gilesc.scalacasts

import com.gilesc.scalacasts.screencast.ScreencastContext
import java.time.LocalTime

object Screencast {
  def apply(cxt: ScreencastContext): Screencast = {
    val timestamp = LocalTime.now()
    new Screencast(
      filePath = cxt.path,
      contentType = cxt.contentType,
      title = cxt.title,
      description = cxt.description,
      tags = cxt.tags,
      created_at = timestamp,
      updated_at = timestamp)
  }

  def apply(path: String, contentType: String, title: String, description: String, tags: String): Screencast = {
    val timestamp = LocalTime.now()

    new Screencast(
      filePath = path,
      contentType = contentType,
      title = title,
      description = description,
      tags = tags.split(",").map(t => Tag(t.trim)).toSet,
      created_at = timestamp,
      updated_at = timestamp)
  }
}

case class Screencast(
  filePath: String,
  contentType: ContentType,
  title: Title,
  description: Description,
  tags: Set[Tag],
  created_at: LocalTime,
  updated_at: LocalTime,
  deleted_at: Option[LocalTime] = None) extends Timestamps

