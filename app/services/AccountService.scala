package app.services

import javax.inject.Inject

import scala.concurrent._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import app.models._

class AccountService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  lazy val Accounts = TableQuery[Accounts]

  def getAccounts: Future[Option[Account]] = db.run(Accounts.result.headOption)
}