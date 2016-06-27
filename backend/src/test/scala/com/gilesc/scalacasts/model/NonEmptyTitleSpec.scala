package com.gilesc.scalacasts.model

import com.gilesc.scalacasts.testing.TestCase
import com.gilesc.util.NonEmptyString

class NonEmptyTitleSpec extends TestCase {
  "Validating a title" should {
    "return a valid title if validated" in {
      val title = "My Title is here"

      println("This is the string version")
      println(title)

      println("This is the non-empty string version")
      println(NonEmptyString(title).toList.head)

      //      val em = Title(title)
      //      em.isRight should be(true)
      //      em.toOption.foreach(println)
      //      em.toOption.foreach(p => p.value should be(title))
    }
  }
}
