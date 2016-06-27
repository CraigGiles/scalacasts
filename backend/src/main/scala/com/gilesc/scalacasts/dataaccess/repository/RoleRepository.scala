package com.gilesc.scalacasts.dataaccess.repository

import java.sql.Timestamp

import com.gilesc.scalacasts.dataaccess.DatabaseProfile
import com.gilesc.scalacasts.model.User
import slick.driver.JdbcProfile
import slick.profile.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

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
class RoleRepository[A <: JdbcProfile] {
  import com.gilesc.scalacasts.dataaccess.Tables._
  //  case class RoleRow(id: Long, name: String, description: String)
  //
  //  class RoleTable(tag: Tag) extends Table[RoleRow](tag, "roles") {
  //    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  //    def name = column[String]("name")
  //    def description = column[String]("description")
  //    def created_at = column[Timestamp]("created_at", SqlType("timestamp not null default CURRENT_TIMESTAMP"))
  //    def updated_at = column[Timestamp]("updated_at", SqlType("timestamp not null default CURRENT_TIMESTAMP"))
  //
  //    override def * = (id, name, description) <> (RoleRow.tupled, RoleRow.unapply)
  //  }
  //
  //  private[this] lazy val RolesTable = TableQuery[RoleTable]

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

  def getRolesForUser(user: User): Future[Seq[RolesRow]] = ???
}
