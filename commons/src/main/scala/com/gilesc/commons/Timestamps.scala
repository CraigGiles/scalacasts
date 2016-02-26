package com.gilesc.commons

import java.time.LocalTime

trait Timestamps {
  val created_at: LocalTime
  val updated_at: LocalTime
  val deleted_at: Option[LocalTime]
}
