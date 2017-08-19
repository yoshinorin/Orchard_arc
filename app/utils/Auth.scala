package utils.auth

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators._
import app.models.Account

trait SessionEnv extends Env {
  type I = Account
  type A = SessionAuthenticator
}
trait JWTEnv extends Env {
  type I = Account
  type A = JWTAuthenticator
}