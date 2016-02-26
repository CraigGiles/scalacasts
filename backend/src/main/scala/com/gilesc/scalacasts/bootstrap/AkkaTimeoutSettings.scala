package com.gilesc.scalacasts.bootstrap

import akka.util.Timeout
import scala.concurrent.duration._
import scala.language.postfixOps

trait AkkaTimeoutSettings {
  implicit val timeout = Timeout(1 second)
}
