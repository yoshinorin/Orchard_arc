package app.services

import javax.inject.Inject

import scala.concurrent._
import scala.concurrent.duration.Duration
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

  def getAccountByUserName(userName: String): Option[Account] = {
    Await.result(db.run(accountsQuery.filter(a => (a.deletedAt.isEmpty) && (a.userName === userName.bind)).result.headOption), Duration.Inf)
  }

  def createAccount(userName: String, password: String, isAdmin: Option[Boolean]): Boolean = {
    if (getAccountByUserName(userName) != None) {
      false
    } else {
      val account = Account(
        id        = 0,
        userName  = userName,
        password  = password,
        isAdmin   = isAdmin,
        createdAt = new java.sql.Timestamp(System.currentTimeMillis()),
        updatedAt = None,
        deletedAt = None
      )
      db.run(accountsQuery += account)
      true
    }
  }

  def updateAccount(userName: String, password: String, isAdmin: Option[Boolean]): Boolean = {
    if (existsAdmin) {
      db.run(accountsQuery.filter(a => (a.deletedAt.isEmpty) && (a.userName === userName.bind))
                          .map(a => a.password -> a.updatedAt)
                          .update(password -> Some(new java.sql.Timestamp(System.currentTimeMillis())))
            )
      true
    } else {
      false
    }
  }

  def deleteAccount(userName: String): Boolean = {
    if (existsAdminExcludeMySelf(userName)) {
      db.run(accountsQuery.filter(a => (a.deletedAt.isEmpty) && (a.userName === userName.bind))
        .map(a => a.deletedAt)
        .update(Some(new java.sql.Timestamp(System.currentTimeMillis())))
      )
      true
    } else {
      false
    }
  }

  private def existsAdmin: Boolean = {
    val admin = Await.result(db.run(accountsQuery.filter(a => (a.deletedAt.isEmpty) && (a.isAdmin === true)).result.headOption), Duration.Inf)
    if (admin != None){
      true
    } else {
      false
    }
  }

  private def existsAdminExcludeMySelf(userName: String): Boolean = {
    val admin = Await.result(db.run(accountsQuery.filter(a => (a.deletedAt.isEmpty) && (a.isAdmin === true) && (a.userName =!= userName)).result.headOption), Duration.Inf)
    if (admin != None){
      true
    } else {
      false
    }
  }
}