package app.services

import javax.inject.{Inject,Singleton}

import scala.concurrent._
import scala.concurrent.duration.Duration
import play.api.mvc.{AnyContent, Request, Result}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import app.models._
import controllers.Secured

@Singleton()
class AccountService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with Secured {
  import profile.api._

  lazy val accountsQuery = TableQuery[Accounts]

  def getAccounts: Seq[Account] = {
    Await.result(db.run(accountsQuery.filter(a => (a.deletedAt.isEmpty)).result), Duration.Inf)
  }

  def getAccountByUserName(userName: String): Option[Account] = {
    Await.result(db.run(accountsQuery.filter(a => (a.userName === userName.bind) && (a.deletedAt.isEmpty)).result.headOption), Duration.Inf)
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
      db.run(accountsQuery.filter(a => (a.userName === userName.bind) && (a.deletedAt.isEmpty))
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
      db.run(accountsQuery.filter(a => (a.userName === userName.bind) && (a.deletedAt.isEmpty))
        .map(a => a.deletedAt)
        .update(Some(new java.sql.Timestamp(System.currentTimeMillis())))
      )
      true
    } else {
      false
    }
  }

  def validateAccount(userName: String, password: String): Boolean = {
    Await.result(db.run(accountsQuery.filter(a => (a.userName === userName.bind) && (a.password === password.bind) && (a.deletedAt.isEmpty)).result.headOption), Duration.Inf) match {
      case Some(_) => true
      case _ => false
    }
  }

  def isAdmin(userName: String): Boolean = {
    Await.result(db.run(accountsQuery.filter(a => (a.userName === userName.bind) && (a.isAdmin === true) && (a.deletedAt.isEmpty)).result.headOption), Duration.Inf) match {
      case Some(_) => true
      case _ => false
    }
  }

  private def existsAdmin: Boolean = {
    Await.result(db.run(accountsQuery.filter(a => (a.isAdmin === true) && (a.deletedAt.isEmpty)).result.headOption), Duration.Inf) match {
      case Some(_) => true
      case _ => false
    }
  }

  private def existsAdminExcludeMySelf(userName: String): Boolean = {
    Await.result(db.run(accountsQuery.filter(a => (a.isAdmin === true) && (a.userName =!= userName) && (a.deletedAt.isEmpty)).result.headOption), Duration.Inf) match {
      case Some(_) => true
      case _ => false
    }
  }

  def withAdmin(f: => String => Request[AnyContent] => Result) = withAuth { userName => implicit rs =>
    if (isAdmin(userName)) {
      f(userName)(rs)
    } else {
      onUnauthorized(rs)
    }
  }
}