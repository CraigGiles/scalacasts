package com.gilesc.scalacasts.screencast

import com.gilesc.scalacasts.model.{ContentType, Description, Tag, Title}

case class ScreencastContext(
  path: String,
  contentType: ContentType,
  title: Title,
  description: Description,
  tags: Set[Tag])
