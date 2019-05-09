package quizleague.web.site.login

import com.felstar.scalajs.vue.VueComponent
import firebase.Firebase
import firebase.auth.{ActionCodeSettings, UserCredential}
import org.scalajs.dom
import org.scalajs.dom.window
import quizleague.web.core.{Module, RouteConfig, _}
import quizleague.web.site.user.{SiteUserService, SiteUserWatchService}
import rxscalajs.{Observable, Subject}
import quizleague.web.model._
import quizleague.web.site.login.LoginCheckComponent.facade
import quizleague.web.site.team.TeamService
import quizleague.web.site.user.SiteUserWatchService.setSiteUserID
import quizleague.web.store.Firestore
import rxscalajs.subjects.ReplaySubject
import quizleague.web.util.Logging._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.literal


object LoginModule extends Module{

  override val components = @@(LoginPage,LoginTitleComponent, ProfileEditComponent, ProfileEditTitleComponent)

  override val routes = @@(
    RouteConfig(path = "/login",
      components = Map("default" -> LoginPage, "title" -> LoginTitleComponent)),
    RouteConfig(path = "/login/signin",
      components = Map("default" -> LoginCheckComponent, "title" -> LoginTitleComponent)),
    RouteConfig(path = "/login/profile",
      components = Map("default" -> ProfileEditComponent, "title" -> ProfileEditTitleComponent)),
    RouteConfig(path = "/login/failed",
      components = Map("default" -> LoginFailedComponent, "title" -> LoginTitleComponent))
  )

      
   
}

class LoggedInUser(val siteUser:SiteUser, val email:String, val team:Team) extends js.Object

object LoginService{

  private val _loggedInUser:Subject[LoggedInUser] = ReplaySubject()

  val userProfile:Observable[LoggedInUser] = _loggedInUser

  Firebase.auth().onAuthStateChanged((user:firebase.User) => {
    if (user != null) {
      val obs =
        SiteUserService
          .siteUserForUid(user.uid)
          .flatMap(suo =>
            suo
              .map(su => TeamService.teamForUser(su.user.id).map(to => to.map(t => (su, t))))
              .getOrElse(Observable.just(None)))

      obs.subscribe(sto => sto.foreach(st => st match {
        case (s, t) => _loggedInUser.next(new LoggedInUser(s, String.valueOf(user.email), t))
      }))
    }
  })

  def logout() = {
    Firebase.auth().signOut()
  }

  def login(email:String, forward:String) = {

    val user = SiteUserService.siteUserForEmail(email)

    user.defaultIfEmpty(js.Array[SiteUser]()).map(su => su.headOption.exists(u => {

      val actionCodeSettings = literal().asInstanceOf[ActionCodeSettings]
      import window.location
      val url = s"https://${location.hostname}/login/signin?forward=$forward"
      actionCodeSettings.url = url
      actionCodeSettings.handleCodeInApp = true

      Firebase.auth().sendSignInLinkToEmail(email, actionCodeSettings)
      window.localStorage.setItem("emailForSignIn", email)
      true
    }))  }

  def check(c:VueComponent, forward:String): Unit ={

    val url = window.location.href
    if (Firebase.auth().isSignInWithEmailLink(url)) {
      // Additional state parameters can also be passed via URL.
      // This can be used to continue the user's intended action before triggering
      // the sign-in operation.
      // Get the email if available. This should be available if the user completes
      // the flow on the same device where they started it.

      val emailFromStorage = window.localStorage.getItem("emailForSignIn")

      val email = if(emailFromStorage == null) {
        window.prompt("Please provide your email for confirmation")
      }
      else{
        emailFromStorage
      }



      // The client SDK will parse the code from the link for you.
      Firebase.auth().signInWithEmailLink(email, url)
        .`then`(result => {

          val siteUser = SiteUserService.siteUserForEmail(String.valueOf(result.user.email))
          siteUser.subscribe(su =>
            su.headOption.foreach(s => {SiteUserService.setUid(s, result.user.uid)
            window.localStorage.removeItem("emailForSignIn")
            c.$router.push(forward)})

          )

        })
        .`catch`( (error:js.Any) => {
          println(error)
          // Some error occurred, you can inspect the code: error.code
          // Common errors could be invalid email and invalid or expired OTPs.
        })
    }
    else {
      println("Invalid sign in address")
    }
  }



}
