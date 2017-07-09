package controllers

import play.api.mvc._
import play.mvc.Security._

trait Secured {

  private def userName(request: RequestHeader) = request.session.get("userName")

  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.HomeController.index)

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(userName, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  //TODO: Have to check an administrator
  def withAdmin(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(userName, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }
}
