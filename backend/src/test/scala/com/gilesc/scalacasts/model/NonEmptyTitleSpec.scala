package com.gilesc.scalacasts.model

import com.gilesc.scalacasts.testing.TestCase

class NonEmptyTitleSpec extends TestCase {
  "Validating a title" should {
    "return a valid title if validated" in {
      val title = "My Title is here"

      val em = Title(title)
      em.isRight should be(true)
      em.toOption.foreach(println)
      em.toOption.foreach(p => p.value should be(title))
    }
  }
}
