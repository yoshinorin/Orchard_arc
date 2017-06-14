package app.services

import scala.concurrent._
import slick.driver.MySQLDriver.api._
import app.models._

trait AccountService extends AccountComponent {
  val db = Database.forConfig("database")
  def getAccounts(id: Option[Long], userName: String, password: String, isAdmin: Option[Boolean]): Future[Option[Account]] =
    db.run(Accounts.result.headOption)
}
