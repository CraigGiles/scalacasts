package com.gilesc.scalacasts.screencast

import java.time.LocalTime
import com.gilesc.scalacasts.Timestamps
import com.gilesc.scalacasts.model.{Tag, Title, ContentType, Description}

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
    this.apply(
      ScreencastContext(
        path,
        ContentType(contentType),
        Title(title),
        Description(description),
        tags.split(",").map(t => Tag(t.trim)).toSet))
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

