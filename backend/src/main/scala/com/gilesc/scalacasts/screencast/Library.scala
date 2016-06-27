package com.gilesc.scalacasts.screencast

import cats.data.{Reader, Xor}
import com.gilesc.scalacasts.model.{Description, ContentType, Tag, Title}
import scala.concurrent.Future
import scala.reflect.io.Path

trait LibraryRepository {
  def add(path: Path, contentType: ContentType, title: Title, description: Description, tags: Set[Tag]): Future[Screencast]
  def remove(title: Title): Future[Int]
  def find(name: Title): Future[List[Screencast]]
  def find(tags: List[Tag]): Future[List[Screencast]]
}

trait ScreencastRepositories {
  val library: LibraryRepository
}

case class ScreencastContext(path: String, contentType: String, title: String, description: String, tags: String)

trait ScreencastLibrary {
  case class ScreencastError(messages: List[String])

  def add(cxt: ScreencastContext): Reader[ScreencastRepositories, Future[Screencast]] =
    Reader((repos: ScreencastRepositories) => {
      val path = Path(cxt.path)
      val ct = ContentType(cxt.contentType)
      val title = Title(cxt.title)
      val tags = cxt.tags.split(",").map(t => Tag(t.trim)).toSet
      val description = Description(cxt.description)

      repos.library.add(path, ct, title, description, tags)
    })

  def find(title: Title): Reader[ScreencastRepositories, Xor[ScreencastError, Option[Screencast]]] = ???
  def find(tags: Set[Tag]): Reader[ScreencastRepositories, Xor[ScreencastError, List[Screencast]]] = ???
}
