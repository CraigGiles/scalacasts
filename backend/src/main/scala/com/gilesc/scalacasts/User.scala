package com.gilesc.scalacasts

import com.gilesc.scalacasts.model.{Email, Username}
import com.gilesc.security.password.HashedPassword

case class User(id: Long, username: Username, email: Email, passwordHash: HashedPassword)

