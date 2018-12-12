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
    <v-layout row>

      <v-text-field v-model.email="email" label="Enter your email address"></v-text-field><v-btn button v-on:click="login(email)" :disabled="!email">Submit</v-btn>
    </v-layout >
    </v-container>
    """
  components(TeamEditComponent)




   def login(email:String) = {

    println("****************** about to send email ************************")

    val actionCodeSettings = literal().asInstanceOf[ActionCodeSettings]
    import window.location
     val url = s"${location.protocol}//${location.hostname}:${location.port}/useredit/index.html#/login/5715999101812736"
     println(url)
     actionCodeSettings.url = url
     actionCodeSettings.handleCodeInApp = true

    Firebase.auth().sendSignInLinkToEmail(email, actionCodeSettings)
     window.localStorage.setItem("emailForSignIn",email)

    println("****************** email sent ************************")

     "Some Text"
  }

  data("email", null)
  method("login")(login _)


  
}



