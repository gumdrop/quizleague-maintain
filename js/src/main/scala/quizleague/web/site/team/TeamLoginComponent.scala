package quizleague.web.site.team

import firebase.Firebase
import firebase.auth._
import org.scalajs.dom._
import quizleague.web.core._
import quizleague.web.useredit.TeamEditComponent

import scala.scalajs.js.Dynamic.literal

object TeamLoginPage extends RouteComponent{
  
  val template = """
    <v-container>
    <v-layout column>
    <v-card>
      <v-card-text>
      <ql-named-text name="login-text"></ql-named-text>
      <v-text-field v-model.email="email" label="Enter your email address"></v-text-field><v-btn button v-on:click="showAlert = login(email)" :disabled="!email">Submit</v-btn>
      <v-alert type="info" transition="scroll-y-transition" :value="showAlert">An email has been sent with login instructions.</v-alert>
     </v-card-text>
     </v-card>
    </v-layout >
    </v-container>
    """
  components(TeamEditComponent)

  data("showAlert", false)


   def login(email:String) = {

    val actionCodeSettings = literal().asInstanceOf[ActionCodeSettings]
    import window.location
     val url = s"https://${location.hostname}/useredit/login/5715999101812736"
     actionCodeSettings.url = url
     actionCodeSettings.handleCodeInApp = true

     Firebase.auth().sendSignInLinkToEmail(email, actionCodeSettings)
     window.localStorage.setItem("emailForSignIn",email)

     true
  }

  data("email", null)
  method("login")(login _)


  
}

object LoginTitleComponent extends RouteComponent{
  val  template="""
    <v-toolbar
      color="amber darken-3"
      dark
      clipped-left>
      <ql-title>Login</ql-title>
      <v-toolbar-title class="white--text" >
        Login
      </v-toolbar-title>
    </v-toolbar>"""



}


