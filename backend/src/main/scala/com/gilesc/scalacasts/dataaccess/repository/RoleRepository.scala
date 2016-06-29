package com.gilesc.scalacasts.dataaccess.repository

import java.sql.Timestamp

import com.gilesc.scalacasts.dataaccess.{Tables, DatabaseProfile}
import com.gilesc.scalacasts.model.User
import slick.driver.JdbcProfile
import slick.profile.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

trait UserRoles {
  sealed trait Role
  final case object Customer extends Role
  final case object ScreencastProducer extends Role
  final case object MemberSupport extends Role
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

  case class RoleId(underlying: Long) extends AnyVal

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

  def getRolesForUser(user: User): DatabaseProfile => Future[Seq[Role]] = { database =>
    import database._
    import database.profile.api._

//    val query = for {
//      users <- Users filter(_.id == user.id)
//      userRoles <- UserRoles if userRoles.userId == users.id
//      roles <- Roles if roles.id == userRoles.roleId
//    } yield (users, userRoles)
    val query = for {
      userRoles <- UserRoles filter(_.userId == user.id)
      roles <- Roles if roles.id == userRoles.roleId
    } yield (roles)

    val something: Future[Seq[Tables.RolesRow]] = execute(query.result)

    Future(Seq.empty[Role])

    /**
      * val query = for {
      artist <- ArtistTable
      album  <- AlbumTable if artist.id === album.artistId
    } yield (artist, album)

      */
    //    Roles.join(UserRoles).on(_.id == _.roleId).
//          join(Users).on(_._2.userId == _.id).
//          filter { tables =>
//          }
  }

  def getRoleId(role: Role): Future[RoleId] = role match {
    case Customer => Future(RoleId(1))
    case ScreencastProducer => Future(RoleId(2))
    case MemberSupport => Future(RoleId(3))
  }
}
