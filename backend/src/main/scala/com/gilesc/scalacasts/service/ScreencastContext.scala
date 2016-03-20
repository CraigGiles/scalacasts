package com.gilesc.scalacasts.service

import com.gilesc.scalacasts.ContentType
import com.gilesc.scalacasts.Title
import com.gilesc.scalacasts.Description
import com.gilesc.scalacasts.Tag

case class ScreencastContext(
  path: String,
  contentType: ContentType,
  title: Title,
  description: Description,
  tags: Set[Tag])
