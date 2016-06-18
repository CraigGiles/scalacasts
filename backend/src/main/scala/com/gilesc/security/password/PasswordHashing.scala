package com.gilesc.security.password

import com.gilesc.scalacasts.model.RawPassword
import org.mindrot.jbcrypt.BCrypt

trait PasswordHashing {
  type Salt = String

  val hash: Salt => RawPassword => HashedPassword = { salt => raw =>
    HashedPassword(BCrypt.hashpw(raw.value, salt), salt)
  }

  val verify: HashedPassword => Boolean = { hashed =>
    BCrypt.checkpw(hashed.password, hashed.salt)
  }
}

