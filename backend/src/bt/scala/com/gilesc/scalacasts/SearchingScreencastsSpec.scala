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

class SearchingScreencastsSpec extends TestCase {
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
      val scalacasts = new Scalacasts()

      val futuresToAwait = for {
        f1 <- scalacasts.add(cxt01)
        f2 <- scalacasts.add(cxt02)
        f3 <- scalacasts.add(cxt03)
      } yield (f1, f2, f3)

      Await.result(futuresToAwait, 10.seconds)
      val future = scalacasts.findByTags(Set(alltag))

      whenReady(future) { results =>
        results.screencasts.size should be(3)
        results.screencasts.foreach { item =>
          item.tags.contains(alltag) should be(true)
        }
      }
    }

    "return only two videos when double tag is requested" in {
      val scalacasts = new Scalacasts()

      val futuresToAwait = for {
        f1 <- scalacasts.add(cxt01)
        f2 <- scalacasts.add(cxt02)
        f3 <- scalacasts.add(cxt03)
      } yield (f1, f2, f3)

      Await.result(futuresToAwait, 10.seconds)

      val future = scalacasts.findByTags(Set(doubletag))
      whenReady(future) { results =>
        results.screencasts.size should be(2)
        results.screencasts.foreach { item =>
          item.tags.contains(doubletag) should be(true)
          item.tags.contains(threetag) should be(false)
        }
      }
    }

    "return an empty-set result if no tags are found" in {
      val scalacasts = new Scalacasts()

      val futuresToAwait = for {
        f1 <- scalacasts.add(cxt01)
        f2 <- scalacasts.add(cxt02)
        f3 <- scalacasts.add(cxt03)
      } yield (f1, f2, f3)

      Await.result(futuresToAwait, 10.seconds)

      val future = scalacasts.findByTags(Set(nonetag))
      whenReady(future) { results =>
        results.screencasts.isEmpty should be(true)
      }
    }
  }
}
