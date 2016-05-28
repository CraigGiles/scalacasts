package com.gilesc.scalacasts

import akka.testkit.TestActorRef
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.util.Success

import com.gilesc.commons.testing.TestCase
import com.gilesc.scalacasts.screencast.ScreencastContext
import scala.concurrent.ExecutionContext.Implicits.global

class AddingScreencastsSpec extends TestCase {
  "uploading a new video to the system" should {
    "store the videos meta information in the video library" in {
      val scalacasts = new Scalacasts()

      val path = "./path/to/myvideo001.mov"
      val contentType = ContentType("mov")
      val title = Title("My Test Video")
      val description = Description("A test video for the library")
      val tags = Set(Tag("test"), Tag("screencast"))
      val cxt = ScreencastContext(path, contentType, title, description, tags)

      scalacasts.add(cxt) onComplete { _ =>
        val resultsFut = scalacasts.findByTitle(title)

        whenReady(resultsFut) { results =>
          results.screencasts.size should be(1)
          results.screencasts.head.title should be(title)
          results.screencasts.head.description should be(description)
          results.screencasts.head.tags should be(tags)
          results.screencasts.head.filePath should be(path)
        }
      }
    }
  }
}
