package com.gilesc.scalacasts.dataaccess.repository

import java.sql.Timestamp

import com.gilesc.scalacasts.dataaccess.repository.RoleRepository.RoleId
import com.gilesc.scalacasts.dataaccess.repository.UserRoles.Customer
import com.gilesc.scalacasts.dataaccess.{Tables, DatabaseProfile}
import com.gilesc.scalacasts.model.User
import slick.driver.JdbcProfile
import slick.profile.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future


trait UserRoles {
  implicit val longToRoleId: Long => RoleId = id => RoleId(id)
  implicit val intToRoleId: Int => RoleId = id => RoleId(id)
  case class RoleId(underlying: Long) extends AnyVal

  sealed case class Role(id: RoleId)
  final case object Customer extends Role(1)
  final case object ScreencastProducer extends Role(2)
  final case object MemberSupport extends Role(3)
}

/**
  * CREATE TABLE IF NOT EXISTS `roles` (
  *   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  *   `name` varchar(40) NOT NULL,
  *   `description` text NOT NULL,
  *   `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  *   `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  *   PRIMARY KEY (`id`)
  * ) ENGINE=InnoDB;
  */
class RoleRepository extends UserRoles {
  import com.gilesc.scalacasts.dataaccess.Tables._

  def insert(name: String, description: String): DatabaseProfile => Future[RolesRow] = { database =>
    import database._
    import database.profile.api._

    val ts = Timestamp.valueOf(java.time.OffsetDateTime.now().toLocalDateTime)

    val insertQuery = Roles returning Roles.map(_.id) into ((role, id) => role.copy(id = id))
    val action = insertQuery += RolesRow(0, name, description, ts, ts)

    execute(action)
  }

  def all(): DatabaseProfile => Future[Seq[RolesRow]] = { database =>
    import database.profile.api._
    database.execute(Roles.result)
  }

  def rolesRowToRole(row: RolesRow) = RoleId(row.id) match {
    case Customer.id => Customer
    case ScreencastProducer.id => ScreencastProducer
    case MemberSupport.id => MemberSupport
  }

  def getRolesForUser(user: User): DatabaseProfile => Future[Seq[Role]] = { database =>
    import database._
    import database.profile.api._

    val query = for {
      userRoles <- UserRoles filter(_.userId == user.id)
      roles <- Roles if roles.id == userRoles.roleId
    } yield roles

    val something: Future[Seq[Tables.RolesRow]] = execute(query.result)

    something map(_.map(rolesRowToRole))
  }

}
