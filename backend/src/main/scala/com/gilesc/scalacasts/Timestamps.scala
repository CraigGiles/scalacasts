package com.gilesc.scalacasts

import java.time.ZonedDateTime

trait Timestamps {
  val created_at: ZonedDateTime
  val updated_at: ZonedDateTime
  val deleted_at: Option[ZonedDateTime]
}