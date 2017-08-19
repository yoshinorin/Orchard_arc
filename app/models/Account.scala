package app.models

import java.sql.Timestamp
import java.util.Date

import slick.driver.MySQLDriver.api._
import com.mohiva.play.silhouette.api.{ Identity, LoginInfo }

class Accounts(tag: Tag) extends Table[Account](tag, "account") {
  val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
  val userName: Rep[String] = column[String]("user_name", O.Length(255,varying=true))
  val password: Rep[String] = column[String]("password", O.Length(255,varying=true))
  val isAdmin: Rep[Option[Boolean]] = column[Option[Boolean]]("is_admin", O.Default(Some(false)))
  val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
  val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  val deletedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("deleted_at", O.Default(None))
  def * = (id, userName, password, isAdmin, createdAt, updatedAt, deletedAt) <> (Account.tupled, Account.unapply)
}

case class Account(
  id: Long,
  userName: String,
  password: String,
  isAdmin: Option[Boolean] = Some(false),
  createdAt: java.sql.Timestamp = new java.sql.Timestamp(System.currentTimeMillis()),
  updatedAt: Option[java.sql.Timestamp] = None,
  deletedAt: Option[java.sql.Timestamp] = None
) extends Identity