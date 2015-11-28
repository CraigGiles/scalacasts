package com.gilesc.commons

import java.time.LocalTime

/**
  * Created by gilesc on 11/28/15.
  */
trait Timestamps {
  val created_at: LocalTime
  val updated_at: LocalTime
  val deleted_at: Option[LocalTime]
}
