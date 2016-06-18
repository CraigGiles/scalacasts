package com.gilesc.scalacasts.model

import com.gilesc.scalacasts.testing.TestCase

/**
  * Created by gilesc on 6/18/16.
  */
class UsernameSpec extends TestCase {

  "Validating usernames" should {
    "return a valid username if correct" in {
      val username = "craiggiles"

      val un = Username(username)
      un.isRight should be(true)
      un.toOption.foreach(p => p.value should be(username))
    }
  }

}
