package quizleague.web.useredit

import scala.scalajs.js
import com.felstar.scalajs.vue.VueRxComponent
import firebase.Firebase
import firebase.auth._
import quizleague.web.core._
import quizleague.web.model.Team
import quizleague.web.site.text.TextService
import quizleague.web.site.team.TeamService
import js.Dynamic.literal
import org.scalajs.dom._

object TeamLoginPage extends RouteComponent{
  
  val template = """
    <v-container>
    <v-layout column>
    <v-card>
      <v-card-text>
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



