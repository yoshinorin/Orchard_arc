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

class AccountController @Inject()(val accountService: AccountService) extends InjectedController with I18nSupport {

  def list = accountService.withAdmin { userName => implicit rs =>
    if (accountService.isAdmin(userName)) {
      Ok(views.html.admin.userlist(accountService.getAccounts))
    } else {
      Redirect(routes.HomeController.index)
    }
  }

  def login = Action { implicit rs =>
    Ok(views.html.account.login(loginForm))
  }

  def authenticate = Action { implicit rs =>
    loginForm.bindFromRequest.fold(
      hasErrors => {
        Ok(views.html.account.login(hasErrors))
      },
      user => {
        if (accountService.validateAccount(user.userName, user.password)) {
          Redirect(routes.HomeController.index).withSession("userName" -> user.userName)
        } else {
          Ok(views.html.account.login(loginForm))
        }
      }
    )
  }

  def logout = Action {
    Redirect(routes.HomeController.index).withNewSession.flashing(
      "success" -> "You've been logged out"
    )
  }

  def edit(id: Option[Long]) = TODO

  def create = TODO

  def update = TODO

  def remove(id: Long) = TODO
}