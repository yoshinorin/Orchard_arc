package app.services

import javax.inject.Inject

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import app.models._

class AccountService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  lazy val accountsQuery = TableQuery[Accounts]

  def getAccounts: Future[Seq[Account]] = {
    db.run(accountsQuery.filter(a => (a.deletedAt.isEmpty)).result)
  }
}
