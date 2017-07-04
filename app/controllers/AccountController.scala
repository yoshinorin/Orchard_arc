package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.i18n.{I18nSupport, MessagesApi}
import app.models._
import app.services.{AccountService, _}
import javax.inject.Inject

import scala.concurrent.Future
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

class AccountController @Inject()(val messagesApi: MessagesApi,
                                  val accountService: AccountService
                                 ) extends Controller with I18nSupport {

  def list = Action { implicit rs =>
    //TODO : Exclude id and password fields
    Ok(views.html.admin.userlist(accountService.getAccounts))
  }

  def dologin = TODO

  def edit(id: Option[Long]) = TODO

  def create = TODO

  def update = TODO

  def remove(id: Long) = TODO
}