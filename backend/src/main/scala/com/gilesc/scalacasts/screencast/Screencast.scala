package com.gilesc.scalacasts.screencast

import com.gilesc.scalacasts.service.ScreencastContext
import java.time.LocalTime

import com.gilesc.commons.Timestamps
import com.gilesc.scalacasts.ContentType
import com.gilesc.scalacasts.Title
import com.gilesc.scalacasts.Description
import com.gilesc.scalacasts.Tag

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
    val cxt = ScreencastContext(path, contentType, title, description, tags)
    Screencast.apply(cxt)
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

