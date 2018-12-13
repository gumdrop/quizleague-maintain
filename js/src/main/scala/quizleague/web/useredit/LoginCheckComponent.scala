package quizleague.web.useredit

import quizleague.web.core.RouteComponent

import scalajs.js
import org.scalajs.dom._
import firebase.Firebase
import firebase.auth._
import quizleague.web.site.team.TeamService
import quizleague.web.store.Firestore

object LoginCheckComponent extends RouteComponent {
  
  val template = """<div>{{check($route.params.id)}}</div>"""

  method("check"){check _ :js.ThisFunction}

  def check(c:facade,id:String): Unit ={
    Firestore.db
    val url = window.location.href
    if (Firebase.auth().isSignInWithEmailLink(url)) {
      // Additional state parameters can also be passed via URL.
      // This can be used to continue the user's intended action before triggering
      // the sign-in operation.
      // Get the email if available. This should be available if the user completes
      // the flow on the same device where they started it.
      var email = window.localStorage.getItem("emailForSignIn")
      if (email == null) {
        // User opened the link on a different device. To prevent session fixation
        // attacks, ask the user to provide the associated email again. For example:
        email = window.prompt("Please provide your email for confirmation")
      }
      // The client SDK will parse the code from the link for you.
      Firebase.auth().signInWithEmailLink(email, url)
        .`then`(result => {

          TeamService.teamForEmail(email).map(_.headOption).subscribe(
            _.foreach{ team =>
              window.localStorage.removeItem("emailForSignIn")
              c.$router.push(s"/useredit/team/${team.id}")}

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