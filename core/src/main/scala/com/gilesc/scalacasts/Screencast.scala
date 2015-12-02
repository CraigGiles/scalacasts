package com.gilesc.scalacasts

object Screencast {
  def apply(path: String, title: String, description: String, tags: String): Screencast = {
    new Screencast(
      filePath = path,
      title = title,
      description = description,
      tags = tags.split(",").map(t => Tag(t.trim)).toSet
    )
  }
}

case class Screencast(filePath: String, title: Title, description: Description, tags: Set[Tag]) {
}
