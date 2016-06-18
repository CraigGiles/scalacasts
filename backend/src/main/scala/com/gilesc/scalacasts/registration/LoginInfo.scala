package com.gilesc.scalacasts.registration

import com.gilesc.scalacasts.model.{RawPassword, Username}

case class LoginInfo(username: Username, password: RawPassword)
