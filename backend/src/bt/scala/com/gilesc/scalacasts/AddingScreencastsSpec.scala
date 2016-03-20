package com.gilesc.scalacasts

import akka.testkit.TestActorRef
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.util.Success

import com.gilesc.commons.testing.TestCase
import com.gilesc.scalacasts.service.ScreencastContext
import com.gilesc.scalacasts.service.Receptionist

class AddingScreencastsSpec extends TestCase {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val timeout = Timeout(10 seconds)

  "uploading a new video to the system" should {
    "store the videos meta information in the video library" in {

      val receptionist = TestActorRef(new Receptionist)
      val path = "./path/to/myvideo001.mov"
      val contentType = ContentType("mov")
      val title = Title("My Test Video")
      val description = Description("A test video for the library")
      val tags = Set(Tag("test"), Tag("screencast"))
      val cxt = ScreencastContext(path, contentType, title, description, tags)

      val addFut = receptionist ? Receptionist.AddNewScreencast(cxt)
      val Success(addResult: Receptionist.Successful) = addFut.value.get
      addResult.isSuccessful should be(true)

      val resultsFut = receptionist ? Receptionist.FindByTitle(cxt.title)
      val Success(results: Receptionist.ScreencastResults) = resultsFut.value.get

      results.screencasts.size should be(1)
      results.screencasts.head.title should be(title)
      results.screencasts.head.description should be(description)
      results.screencasts.head.tags should be(tags)
      results.screencasts.head.filePath should be(path)
    }

  }
}
