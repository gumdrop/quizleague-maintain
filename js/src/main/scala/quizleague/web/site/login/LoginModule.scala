package quizleague.web.site.login

import com.felstar.scalajs.vue.VueComponent
import firebase.Firebase
import firebase.auth.{ActionCodeSettings, UserCredential}
import firebase.firestore.DocumentSnapshot
import org.scalajs.dom.window
import quizleague.web.core.{Module, RouteConfig, _}
import quizleague.web.model._
import quizleague.web.site.team.TeamService
import quizleague.web.site.user.SiteUserService
import rxscalajs.facade.ObservableFacade
import rxscalajs.subjects.ReplaySubject
import rxscalajs.{Observable, Subject}
import quizleague.web.util.Logging.log


import scala.scalajs.js
import scala.scalajs.js.Dynamic.literal
import scala.scalajs.js.|


object LoginModule extends Module{

  override val components = @@(LoginPage,LoginTitleComponent, ProfileEditComponent, ProfileEditTitleComponent)

  override val routes = @@(
    RouteConfig(path = "/login",
      components = Map("default" -> LoginPage, "title" -> LoginTitleComponent),
      beforeEnter = LoginService.noAuthRouteGuard _),
    RouteConfig(path = "/login/signin",
      components = Map("default" -> LoginCheckComponent, "title" -> LoginTitleComponent),
      beforeEnter = LoginService.noAuthRouteGuard _),
    RouteConfig(path = "/login/profile",
      components = Map("default" -> ProfileEditComponent, "title" -> ProfileEditTitleComponent),
      beforeEnter = LoginService.routeGuard _),
    RouteConfig(path = "/login/failed",
      components = Map("default" -> LoginFailedComponent, "title" -> LoginTitleComponent))
  )

      
   
}

class LoggedInUser(val siteUser:SiteUser, val email:String, val team:Team) extends js.Object

object EmailValidationStatus extends Enumeration {
  type EmailValidationStatus = Value
  val invalid, registered, unregistered = Value
}

object LoginService{

  private val _loggedInUser:Subject[LoggedInUser] = ReplaySubject()
  _loggedInUser.next(null)

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
    else{
      _loggedInUser.next(null)
    }
  })

  def logout() = {
    Firebase.auth().signOut()
  }

  def login(email:String, forward:String) = {

    val user = SiteUserService.siteUserForEmail(email)

    user.defaultIfEmpty(Option.empty[SiteUser]).map(su => su.exists(u => {

      val actionCodeSettings = literal().asInstanceOf[ActionCodeSettings]
      import window.location
      val url = s"https://${location.hostname}/login/signin?forward=$forward"
      actionCodeSettings.url = url
      actionCodeSettings.handleCodeInApp = true

      Firebase.auth().sendSignInLinkToEmail(email, actionCodeSettings)
      window.localStorage.setItem("emailForSignIn", email)
      true
    }))  }

  def verifyEmail(email:String) = {
    import EmailValidationStatus._
    val user = SiteUserService.siteUserForEmail(email)
    user.map(su => su.map(u => u.uid.fold(unregistered)(uid => registered))).defaultIfEmpty(Option(invalid)).map(_.getOrElse(invalid))
  }

  def loginWithPassword(c:VueComponent,email:String, password:String, forward:String) = {
    val user = SiteUserService.siteUserForEmail(email)

    val subject = ReplaySubject[Boolean]()

    user.defaultIfEmpty(Option.empty[SiteUser]).subscribe(su => su.exists(u => {
      Firebase.auth().signInWithEmailAndPassword(email,password)
        .`then`(handleLoginSuccess(c, forward))
        .`catch`(err => {
          log("Error on login",err.message)
          subject.error(err.code match{
            case _ => err.message
          })})
      true
    }))
    subject.asObservable()
  }

  def createAcccount(c: VueComponent, email:String, password:String, forward:String) = {
    val user = SiteUserService.siteUserForEmail(email)

    val subject = ReplaySubject[Boolean]()

    user.defaultIfEmpty(Option.empty[SiteUser]).subscribe(su => su.exists(u => {
      Firebase.auth().createUserWithEmailAndPassword(email,password)
        .`then`(handleLoginSuccess(c,forward))
        .`catch`(err => {
          log("Error on create",err.message)
          subject.error(err.code match{
            case "auth/email-already-in-use" => "Email is already in use"
            case "auth/weak-password" => "Password is too weak. Try using numbers, uppercase letters and symbols as well as lowercase letters."
            case _ => err.message
          })
        })
      true
    }))
    subject.asObservable()
  }


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
        .`then`(handleLoginSuccess(c,forward))
        .`catch`(handleLoginFailure(c))
    }
    else {
      println("Invalid sign in address")
    }
  }

  private def handleLoginSuccess(c:VueComponent,forward:String)(result:UserCredential) = {
    val siteUser = SiteUserService.siteUserForEmail(String.valueOf(result.user.email))
    siteUser.subscribe(su =>
      su.headOption.foreach(s => {SiteUserService.setUid(s, result.user.uid)
        window.localStorage.removeItem("emailForSignIn")
        if(result.additionalUserInfo.fold(false)(_.isNewUser)){
          c.$router.push(s"/login/profile?first=true&forward=$forward")
        }
        else{
          c.$router.push(forward)
        }
      })

    )
  }
  private def handleLoginFailure(c:VueComponent)(error:js.Any) ={
    println(error)
    c.$router.push("/login/failed")
  }

  def routeGuard(from:js.Any, to:js.Any, next:js.Function0[Unit]|js.Function1[Boolean | String | Exception, Unit]):Unit = {
    userProfile.subscribe(u => if(u != null){next.asInstanceOf[js.Function0[Unit]]()})
  }

  def noAuthRouteGuard(from:js.Any, to:js.Any, next:js.Function0[Unit]|js.Function1[Boolean | String | Exception, Unit]):Unit = {
    userProfile.subscribe(u => if(u == null){next.asInstanceOf[js.Function0[Unit]]()})
  }

}

