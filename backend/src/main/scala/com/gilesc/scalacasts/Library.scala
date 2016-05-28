package com.gilesc.scalacasts

import scala.concurrent.Future

trait LibraryRepository {
  def add(screencast: Screencast): Future[Int]
  def remove(title: Title): Future[Int]
  def findByTitle(name: Title): Future[List[Screencast]]
  def findByTags(tags: List[Tag]): Future[List[Screencast]]
}
