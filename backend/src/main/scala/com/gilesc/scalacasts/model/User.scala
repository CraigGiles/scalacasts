package com.gilesc.scalacasts.model

import com.gilesc.security.password.HashedPassword

/**
  * Created by gilesc on 6/19/16.
  */
case class User(id: Long, username: Username, email: Email, passwordHash: HashedPassword)
