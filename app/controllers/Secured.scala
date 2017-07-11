package controllers

import play.api.mvc._

trait Secured {

  private def userName(request: RequestHeader) = request.session.get("userName")

  protected def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.HomeController.index)

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(userName, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }
}
