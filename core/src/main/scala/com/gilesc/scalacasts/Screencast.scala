package com.gilesc.scalacasts

import java.time.LocalTime

import com.gilesc.commons.Timestamps

object Screencast {
  def apply(path: String, title: String, description: String, tags: String): Screencast = {
    val timestamp = LocalTime.now()

    new Screencast(
      filePath = path,
      title = title,
      description = description,
      tags = tags.split(",").map(t => Tag(t.trim)).toSet,
      created_at = timestamp,
      updated_at = timestamp
    )
  }
}

case class Screencast(filePath: String,
                      title: Title,
                      description: Description,
                      tags: Set[Tag],
                      created_at: LocalTime,
                      updated_at: LocalTime,
                      deleted_at: Option[LocalTime] = None) extends Timestamps

