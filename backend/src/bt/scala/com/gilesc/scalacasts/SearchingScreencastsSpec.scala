package com.gilesc.scalacasts

import akka.testkit.TestActorRef
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.util.Success

import com.gilesc.commons.testing.TestCase
import com.gilesc.scalacasts.screencast.ScreencastContext

class SearchingScreencastsSpec extends TestCase {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val timeout = Timeout(10 seconds)

  val path = "./path/to/myvideo001.mov"
  val contentType = ContentType("mov")
  val title = Title("My Test Video")
  val description = Description("A test video for the library")
  val tags01 = Set(Tag("all"), Tag("one"), Tag("double"))
  val tags02 = Set(Tag("all"), Tag("two"), Tag("double"))
  val tags03 = Set(Tag("all"), Tag("three"))
  val cxt01 = ScreencastContext(path, contentType, "001", description, tags01)
  val cxt02 = ScreencastContext(path, contentType, "002", description, tags02)
  val cxt03 = ScreencastContext(path, contentType, "003", description, tags03)
  val alltag = Tag("all")
  val doubletag = Tag("double")

  "finding screencasts by tag" should {
    "returns the proper amount of videos when tags are requested" in {
      val scalacasts = TestActorRef(new Scalacasts)

      scalacasts ! Scalacasts.AddNewScreencast(cxt01)
      scalacasts ! Scalacasts.AddNewScreencast(cxt02)
      scalacasts ! Scalacasts.AddNewScreencast(cxt03)

      val allTagFut = scalacasts ? Scalacasts.FindByTags(Set(alltag))
      val Success(results: Scalacasts.ScreencastResults) = allTagFut.value.get

      results.screencasts.size should be(3)
      results.screencasts.foreach { item =>
        item.tags.contains(alltag) should be(true)
      }
    }

    "return only two videos when double tag is requested" in {
      val scalacasts = TestActorRef(new Scalacasts)

      scalacasts ! Scalacasts.AddNewScreencast(cxt01)
      scalacasts ! Scalacasts.AddNewScreencast(cxt02)
      scalacasts ! Scalacasts.AddNewScreencast(cxt03)

      val future = scalacasts ? Scalacasts.FindByTags(Set(doubletag))
      val Success(results: Scalacasts.ScreencastResults) = future.value.get

      results.screencasts.size should be(2)
      results.screencasts.foreach { item =>
        item.tags.contains(doubletag) should be(true)
        item.tags.contains(Tag("three")) should be(false)
      }
    }
  }
}
