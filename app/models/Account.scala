package app.models

trait AccountComponent {
  val profile: slick.driver.JdbcProfile
  import profile.api._

  lazy val Accounts = TableQuery[Accounts]

  class Accounts(tag: Tag) extends Table[Account](tag, "ACCOUNT") {
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    val userName: Rep[String] = column[String]("USER_NAME", O.Length(255,varying=true))
    val password: Rep[String] = column[String]("PASSWORD", O.Length(255,varying=true))
    val isAdmin: Rep[Option[Boolean]] = column[Option[Boolean]]("IS_ADMIN", O.Default(Some(false)))
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("CREATED_AT")
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("UPDATED_AT", O.Default(None))
    val deletedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("DELETED_AT", O.Default(None))
    def * = (id, userName, password, isAdmin, createdAt, updatedAt, deletedAt) <> (Account.tupled, Account.unapply)
  }
}

case class Account(
  id: Long,
  userName: String,
  password: String,
  isAdmin: Option[Boolean] = Some(false),
  createdAt: Option[java.sql.Timestamp],
  updatedAt: Option[java.sql.Timestamp] = None,
  deletedAt: Option[java.sql.Timestamp] = None
)