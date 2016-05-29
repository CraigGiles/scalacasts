package com.gilesc.scalacasts

import akka.pattern.ask
import akka.testkit.TestActorRef
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Success

import com.gilesc.scalacasts.screencast.Scalacasts
import com.gilesc.scalacasts.screencast.Screencast
import com.gilesc.scalacasts.screencast.ScreencastContext
import com.gilesc.scalacasts.testing.TestCase
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
          results.size should be(1)
          results.head.title should be(title)
          results.head.description should be(description)
          results.head.tags should be(tags)
          results.head.filePath should be(path)
        }
      }
    }
  }
}
