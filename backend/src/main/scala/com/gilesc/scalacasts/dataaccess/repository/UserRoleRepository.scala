//package com.gilesc.scalacasts.dataaccess.repository
//
//import java.sql.Timestamp
//import java.time.OffsetDateTime
//
//import com.gilesc.scalacasts.User
//import com.gilesc.scalacasts.dataaccess.DatabaseProfile
//import com.gilesc.scalacasts.model.UserRole
//import com.gilesc.scalacasts.registration.UserId
//import com.gilesc.security.password.PasswordHashing
//import slick.driver.JdbcProfile
//import slick.profile.SqlProfile.ColumnOption.SqlType
//
//import scala.concurrent.Future
//
///**
//  * CREATE TABLE IF NOT EXISTS `user_roles` (
//  *   `user_id` bigint(20) unsigned NOT NULL,
//  *   `role_id` bigint(20) unsigned NOT NULL,
//  *   `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
//  * ) ENGINE=InnoDB;
//  */
//class UserRoleRepository[A <: JdbcProfile](override val profile: JdbcProfile) extends DatabaseProfile with PasswordHashing {
//  import profile.api._
//
//  case class UserRoleRow(userId: Long, roleId: Long, createdAt: Option[OffsetDateTime])
//
//  class UserRolesTable(tag: Tag) extends Table[UserRoleRow](tag, "users") {
//    def user_id = column[Long]("user_id")
//    def role_id = column[Long]("role_id")
//    def created_at = column[Timestamp]("created_at", SqlType("timestamp not null default CURRENT_TIMESTAMP"))
//
//    override def * =
//      (user_id, role_id, created_at) <> (UserRoleRow.tupled, UserRoleRow.unapply)
//  }
//
//  private[this] lazy val UserRolesTable = TableQuery[UserRolesTable]
//
//  def addRoleForUser(user: UserId, roles: List[UserRole]): Future[Int] = {
//    val newRoles = roles.map { r =>
//      UserRoleRow(user, r, None)
//    }
//    val action = UserRolesTable + ""
//    Future.successful(1)
//  }
//  def insert(user: User, roles: List[UserRole]): Future[Long] = {
//    //    val insertQuery = UsersTable returning UsersTable.map(_.id) into ((user, id) => user.copy(id = id))
//    //    val hashed = hash(password)
//    //    val action = insertQuery += User(0, name, email, hashed.password)
//
//    execute(action)
//
//    Future.successful(1L)
//  }
//
//  implicit val columnType: BaseColumnType[UserRole] =
//    MappedColumnType.base[UserRole, Int](toInt, fromInt)
//
//  private def fromInt(role: UserRole): UserRole = role match {
//    case Roles.Customer => 1
//    case Roles.ContentProducer => 1
//    case Roles.MemberSupport => 1
//    //    case 5 => Awesome
//    //    case 4 => Good
//    //    case 3 => NotBad
//    //    case 2 => Meh
//    //    case 1 => Aaargh
//    case _ => sys.error("Ratings only apply from 1 to 5")
//  }
//
//  private def toInt(rating: UserRole): Int = rating match {
//    //    case Awesome => 5
//    //    case Good => 4
//    //    case NotBad => 3
//    //    case Meh => 2
//    //    case Aaargh => 1
//    case _ => 1
//  }
//}
