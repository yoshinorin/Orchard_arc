package utils.auth

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators._
import app.models.UserIdentify

trait SessionEnv extends Env {
  type I = UserIdentify
  type A = SessionAuthenticator
}
trait JWTEnv extends Env {
  type I = UserIdentify
  type A = JWTAuthenticator
}