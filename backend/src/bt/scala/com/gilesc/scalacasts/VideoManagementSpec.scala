package com.gilesc.scalacasts

import akka.testkit.TestActorRef
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.util.Success

import com.gilesc.commons.testing.TestCase
import com.gilesc.scalacasts.screencast.ScreencastContext

class VideoManagementSpec extends TestCase {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val timeout = Timeout(10 seconds)

  "The video management system" should {
    "be able to upload a video to the video library" in {

      given("the user gives the video information to the system")
      val receptionist = TestActorRef(new Scalacasts)
      val path = "./path/to/myvideo001.mov"
      val contentType = ContentType("mov")
      val title = Title("My Test Video")
      val description = Description("A test video for the library")
      val tags = Set(Tag("test"), Tag("screencast"))
      val sc = ScreencastContext(path, contentType, title, description, tags)

      when("the video gets uploaded")
      receptionist ! Scalacasts.AddNewScreencast(sc)

      then("the video appears in the library with proper meta information")
      val results = receptionist ? Scalacasts.FindByTitle(sc.title)

      results.map { result =>
        val list = result.asInstanceOf[Seq[Screencast]]
        list.size should be(1)
        list.head.title should be(title)
        list.head.description should be(description)
        list.head.tags should be(tags)
        list.head.filePath should be(path)
      }
    }
  }
}
