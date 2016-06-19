package com.gilesc.scalacasts.dataaccess.repository

import java.sql.Timestamp

import com.gilesc.scalacasts.dataaccess.{DatabaseProfile, Tables}
import com.gilesc.scalacasts.model.{User, Email, RawPassword, Username}
import com.gilesc.security.password.{HashedPassword, PasswordHashing}
import com.typesafe.scalalogging.LazyLogging
import org.mindrot.jbcrypt.BCrypt
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * CREATE TABLE IF NOT EXISTS `users` (
  * `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  * `username` varchar(30) NOT NULL,
  * `email` varchar(120) NOT NULL,
  * `password_hash` char(70) NOT NULL,
  * `password_salt` varchar(255) NOT NULL,
  * `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  * `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  * `deleted_at` timestamp NULL DEFAULT NULL,
  * PRIMARY KEY (`id`),
  * UNIQUE KEY `username` (`username`),
  * UNIQUE KEY `email` (`email`)
  *
  * ) ENGINE=InnoDB;
  */
class UserRepository[A <: JdbcProfile]
    extends DatabaseProfile
    with PasswordHashing
    with LazyLogging {

  import profile.api._
  import com.gilesc.scalacasts.dataaccess.Tables._

  def insert(name: Username, email: Email, password: RawPassword)(implicit ec: ExecutionContext): Future[User] = {
    logger.info("Inserting with name: {}, email: {}, password: {}", name.value, email.value, password.value)
    val hashWithSalt = hash(BCrypt.gensalt())

    val usersInsertQuery = Users returning Users.map(_.id) into ((user, id) => user.copy(id = id))
    val ts = Timestamp.valueOf(java.time.OffsetDateTime.now().toLocalDateTime)
    val hashed = hashWithSalt(password)
    val action = usersInsertQuery += UsersRow(0, name.value, email.value, hashed.password, hashed.salt, ts, ts, None)
    val CustomerRole = 1

    execute(action) map { row =>
      execute(UserRoles += UserRolesRow(row.id, CustomerRole, ts))

      usersRowToUser(row)
    }
  }

  def findByUsername(username: String)(implicit ec: ExecutionContext): Future[Option[User]] =
    execute(Users.filter(_.username === username).take(1).result) map (_.headOption) map {
      case None => None
      case Some(row) => Some(usersRowToUser(row))
    }

  def findByEmail(email: String)(implicit ec: ExecutionContext): Future[Option[User]] =
    execute(Users.filter(_.email === email).take(1).result) map (_.headOption) map {
      case None => None
      case Some(row) => Some(usersRowToUser(row))
    }

  private[this] val usersRowToUser: Tables.UsersRow => User = { row =>
    (for {
      un <- Username(row.username)
      em <- Email(row.email)
    } yield User(row.id, un, em, HashedPassword(row.passwordHash, row.passwordSalt))).toList.head
  }
}

