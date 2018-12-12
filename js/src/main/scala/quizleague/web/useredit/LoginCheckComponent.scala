package quizleague.web.useredit

import quizleague.web.core.RouteComponent

import scalajs.js
import org.scalajs.dom._
import firebase.Firebase
import firebase.auth._
import quizleague.web.store.Firestore

object LoginCheckComponent extends RouteComponent {
  
  val template = """<div>{{check($route.params.id)}}</div>"""
  
  //override val activated = ((c:facade) => {println("activated");c.$router.push("/team/5715999101812736")}):js.ThisFunction

  method("check"){check _ :js.ThisFunction}

  def check(c:facade,id:String): Unit ={
    Firestore.db
    println("before auth")
    c.$router.push(s"team/5715999101812736")
    return

    //val url = """https://chiltern-ql-firestore.firebaseapp.com/__/auth/action?apiKey=AIzaSyBs6LpcOSpLMlKlzw0aPB6Ie-39mqlKrm8&mode=signIn&oobCode=12B-SYqGxMiia6Eu5PZ7iAyUvV3KXuQM-Vgj5JKCbrEAAAFnYXoQRw&continueUrl=http://localhost:3000/useredit/login/5715999101812736&lang=en"""//window.location.href
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

          println(s"auth success! : id = $id")

          val testid = "5715999101812736"

          // Clear email from storage.
          window.localStorage.removeItem("emailForSignIn")
          c.$router.push(s"team/$testid")
          // You can access the new user via result.user
          // Additional user info profile not available via:
          // result.additionalUserInfo.profile == null
          // You can check if the user is new or existing:
          // result.additionalUserInfo.isNewUser
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