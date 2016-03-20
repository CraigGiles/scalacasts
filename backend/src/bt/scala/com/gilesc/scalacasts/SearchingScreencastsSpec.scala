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

class SearchingScreencastsSpec extends TestCase {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val timeout = Timeout(10 seconds)

  val path = "./path/to/myvideo001.mov"
  val contentType = ContentType("mov")
  val title = Title("My Test Video")
  val description = Description("A test video for the library")
  val alltag = Tag("all")
  val doubletag = Tag("double")
  val threetag = Tag("three")
  val nonetag = Tag("none")

  val tags01 = Set(alltag, Tag("one"), doubletag)
  val tags02 = Set(alltag, doubletag, Tag("two"))
  val tags03 = Set(alltag, threetag)
  val cxt01 = ScreencastContext(path, contentType, "001", description, tags01)
  val cxt02 = ScreencastContext(path, contentType, "002", description, tags02)
  val cxt03 = ScreencastContext(path, contentType, "003", description, tags03)

  "finding screencasts by tag" should {
    "returns the proper amount of videos when tags are requested" in {
      val scalacasts = TestActorRef(new Receptionist)

      scalacasts ! Receptionist.AddNewScreencast(cxt01)
      scalacasts ! Receptionist.AddNewScreencast(cxt02)
      scalacasts ! Receptionist.AddNewScreencast(cxt03)

      val future = scalacasts ? Receptionist.FindByTags(Set(alltag))
      val Success(results: Receptionist.ScreencastResults) = future.value.get

      results.screencasts.size should be(3)
      results.screencasts.foreach { item =>
        item.tags.contains(alltag) should be(true)
      }
    }

    "return only two videos when double tag is requested" in {
      val scalacasts = TestActorRef(new Receptionist)

      scalacasts ! Receptionist.AddNewScreencast(cxt01)
      scalacasts ! Receptionist.AddNewScreencast(cxt02)
      scalacasts ! Receptionist.AddNewScreencast(cxt03)

      val future = scalacasts ? Receptionist.FindByTags(Set(doubletag))
      val Success(results: Receptionist.ScreencastResults) = future.value.get

      results.screencasts.size should be(2)
      results.screencasts.foreach { item =>
        item.tags.contains(doubletag) should be(true)
        item.tags.contains(threetag) should be(false)
      }
    }

    "return an empty-set result if no tags are found" in {
      val scalacasts = TestActorRef(new Receptionist)

      scalacasts ! Receptionist.AddNewScreencast(cxt01)
      scalacasts ! Receptionist.AddNewScreencast(cxt02)
      scalacasts ! Receptionist.AddNewScreencast(cxt03)

      val future = scalacasts ? Receptionist.FindByTags(Set(nonetag))
      val Success(results: Receptionist.ScreencastResults) = future.value.get

      results.screencasts.isEmpty should be(true)
    }
  }
}
