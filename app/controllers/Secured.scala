package controllers

import play.api.mvc._

trait Secured {

  private def userName(request: RequestHeader) = request.session.get("userName")

  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.HomeController.index)

  //def withAdmin = TODO

  //def withUser = TODO
}
