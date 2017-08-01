package controllers.api

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import javax.inject.Inject

import app.models.Account
import app.services.{AccountService, _}

object  AccountApi {

  implicit val accountWriteFormat = new Writes[Account] {
    def writes(account: Account): JsValue = {
      Json.obj(
        "id" -> account.id,
        "userName" -> account.userName,
        "createdAt" -> account.createdAt,
        "updatedAt" -> account.updatedAt
      )
    }
  }

}

class AccountApi @Inject()(val accountService: AccountService) extends InjectedController {

  import AccountApi._

  def list = TODO

  def authenticate = TODO

  def logout = TODO

}
