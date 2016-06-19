package com.gilesc.scalacasts.screencast

import java.time.ZonedDateTime

import com.gilesc.scalacasts.Timestamps
import com.gilesc.scalacasts.model.{ContentType, Description, Tag, Title}

object Screencast {
  def apply(cxt: ScreencastContext): Screencast = {
    val timestamp = ZonedDateTime.now()
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
  created_at: ZonedDateTime,
  updated_at: ZonedDateTime,
  deleted_at: Option[ZonedDateTime] = None) extends Timestamps

