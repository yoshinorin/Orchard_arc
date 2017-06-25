package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.db.slick._
import slick.driver.JdbcProfile
import app.models.{Account}
import javax.inject.Inject
import scala.concurrent.Future
import slick.driver.H2Driver.api._
import AccountController._

object AccountController {
  case class AccountForm(id: Option[Long], userName: String, password: String, isAdmin: Option[Boolean])
  case class LoginForm(userName: String, password: String)

  val accountForm = Form(
    mapping(
      "id"          -> optional(longNumber),
      "userName"    -> nonEmptyText(maxLength = 255),
      "password"    -> nonEmptyText(maxLength = 255),
      "isAdmin"     -> optional(boolean)
    )(AccountForm.apply)(AccountForm.unapply)
  )

  val loginForm = Form(
    mapping(
      "userName"    -> nonEmptyText(maxLength = 255),
      "password"    -> nonEmptyText(maxLength = 255)
    )(LoginForm.apply)(LoginForm.unapply)
  )
}

class AccountController @Inject()(val dbConfigProvider: DatabaseConfigProvider,
                                  val messagesApi: MessagesApi) extends Controller
    with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  def list = TODO

  def edit(id: Option[Long]) = TODO

  def create = TODO

  def update = TODO

  def remove(id: Long) = TODO
}