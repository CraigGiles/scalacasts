package com.gilesc.scalacasts.dataaccess.repository

import java.sql.Timestamp

import com.gilesc.scalacasts.User
import com.gilesc.scalacasts.dataaccess.DatabaseProfile
import com.gilesc.scalacasts.model.Roles
import com.gilesc.security.password.PasswordHashing
import slick.driver.JdbcProfile
import slick.profile.SqlProfile.ColumnOption.SqlType

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
class UserRepository[A <: JdbcProfile](override val profile: JdbcProfile) extends DatabaseProfile with PasswordHashing {
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

  def insert(name: String, email: String, password: String): Future[User] = {
    val usersInsertQuery = UsersTable returning UsersTable.map(_.id) into ((user, id) => user.copy(id = id))
    val hashed = hash(password)
    val action = usersInsertQuery += User(0, name, email, hashed.password)

    val ac = (for {
      // Insert the user into the users table
      usr <- usersInsertQuery += User(0, name, email, hashed.password)
      _ <- sqlu"""INSERT INTO user_roles (user_id, role_id) VALUES (${usr.id}, ${Roles.Customer.id}"""
    } yield usr).transactionally

    //    val a = (for {
    //      ns <- coffees.filter(_.name.startsWith("ESPRESSO")).map(_.name).result
    //      _ <- DBIO.seq(ns.map(n => coffees.filter(_.name === n).delete): _*)
    //    } yield ()).transactionally
    //
    //    val f: Future[Unit] = db.run(a)

    execute(action)
  }

  def findByEmail(email: String) =
    execute(UsersTable.filter(_.email === email).take(1).result) map (_.headOption)
}

