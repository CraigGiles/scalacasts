package com.gilesc.scalacasts.dataaccess

import java.sql.Timestamp

import com.gilesc.scalacasts.User
import com.gilesc.scalacasts.registration.RegistrationContext
import slick.driver.JdbcProfile
import slick.profile.SqlProfile.ColumnOption.SqlType

/**
  * CREATE TABLE IF NOT EXISTS `users` (
  * `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  * `username` varchar(30) NOT NULL,
  * `email` varchar(120) NOT NULL,
  * `password_hash` char(70) NOT NULL,
  * `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  * `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  * `deleted_at` timestamp NULL DEFAULT NULL,
  * PRIMARY KEY (`id`),
  * UNIQUE KEY `username` (`username`),
  * UNIQUE KEY `email` (`email`)
  *
  * ) ENGINE=InnoDB;
  */
class UserRepository[A <: JdbcProfile](override val profile: JdbcProfile) extends DatabaseProfile {
  import profile.api._

  import scala.concurrent.ExecutionContext.Implicits.global

  class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def email = column[String]("email")
    def password_hash = column[String]("password_hash")
    def created_at = column[Timestamp]("created_at", SqlType("timestamp not null default CURRENT_TIMESTAMP"))
    def updated_at = column[Timestamp]("updated_at", SqlType("timestamp not null default CURRENT_TIMESTAMP"))
    def deleted_at = column[Option[Timestamp]]("deleted_at", SqlType("timestamp null default null"))

    override def * =
      (id, username, email, password_hash) <> (User.tupled, User.unapply)
  }

  private[this] lazy val UsersTable = TableQuery[UserTable]

  def findByEmail(email: String) =
    execute(UsersTable.filter(_.email === email).take(1).result) map (_.headOption)
  def insert(cxt: RegistrationContext) = UsersTable += User(0L, cxt.username, cxt.email, cxt.password, )
}

