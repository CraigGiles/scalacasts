package com.gilesc.scalacasts.dataaccess.repository

import java.sql.Timestamp

import com.gilesc.scalacasts.User
import com.gilesc.scalacasts.dataaccess.DatabaseProfile
import com.gilesc.security.password.PasswordHashing
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile

import scala.concurrent.Future

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
class UserRepository[A <: JdbcProfile](override val profile: JdbcProfile)
    extends DatabaseProfile
    with PasswordHashing
    with LazyLogging {

  import profile.api._
  import com.gilesc.scalacasts.dataaccess.Tables._

  import scala.concurrent.ExecutionContext.Implicits.global

  def insert(name: String, email: String, password: String): Future[User] = {
    logger.info("Inserting with name: {}, email: {}, password: {}", name, email, password)
    val usersInsertQuery = Users returning Users.map(_.id) into ((user, id) => user.copy(id = id))
    val ts = Timestamp.valueOf(java.time.OffsetDateTime.now().toLocalDateTime)
    val hashed = hash(password)
    val action = usersInsertQuery += UsersRow(0, name, email, hashed.password, ts, ts, None)
    val CustomerRole = 1

    execute(action) map { row =>
      execute(UserRoles += UserRolesRow(row.id, CustomerRole, ts))
      User(row.id, row.username, row.email, row.passwordHash)
    }
  }

  def findByEmail(email: String) =
    execute(Users.filter(_.email === email).take(1).result) map (_.headOption)
}

