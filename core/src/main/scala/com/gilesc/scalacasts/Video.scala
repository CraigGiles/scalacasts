package com.gilesc.scalacasts

import java.time.LocalTime

import com.gilesc.commons.Timestamps

case class Video(id: Long,
                 title: Title,
                 tags: Set[Tag],
                 created_at: LocalTime,
                 updated_at: LocalTime,
                 deleted_at: Option[LocalTime] = None) extends Timestamps


