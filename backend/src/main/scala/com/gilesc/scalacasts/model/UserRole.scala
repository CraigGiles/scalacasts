package com.gilesc.scalacasts.model

sealed abstract class UserRole(val id: Long)
//sealed abstract class Rating(val stars: Int)

object Roles {
  final case object Customer extends UserRole(1)
  final case object ContentProducer extends UserRole(2)
  final case object MemberSupport extends UserRole(3)

  //  final case object Awesome extends Rating(5)
  //  final case object Good extends Rating(4)
  //  final case object NotBad extends Rating(3)
  //  final case object Meh extends Rating(2)
  //  final case object Aaargh extends Rating(1)

}
