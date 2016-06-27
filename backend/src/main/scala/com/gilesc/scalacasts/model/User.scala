package com.gilesc.scalacasts.model

import com.gilesc.security.password.HashedPassword

case class User(id: Long, username: Username, email: Email, passwordHash: HashedPassword)
