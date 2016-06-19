package com.gilesc.scalacasts.screencast

import java.time.ZonedDateTime

import com.gilesc.scalacasts.Timestamps
import com.gilesc.scalacasts.model.{ContentType, Description, Tag, Title}

import scala.reflect.io.Path

case class Screencast(
    filePath: String,
    contentType: ContentType,
    title: Title,
    description: Description,
    tags: Set[Tag],
    created_at: ZonedDateTime,
    updated_at: ZonedDateTime,
    deleted_at: Option[ZonedDateTime] = None) extends Timestamps {

  def apply(path: Path, contentType: ContentType, title: Title, description: Description, tags: Set[Tag]): Screencast = {
    val ts = ZonedDateTime.now()
    Screencast(path.path, contentType, title, description, tags, ts, ts, None)
  }
}

