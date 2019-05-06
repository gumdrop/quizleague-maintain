package quizleague.web.site.login

import com.felstar.scalajs.vue.{VueComponent, VueRxComponent}
import firebase.Firebase
import firebase.auth._
import org.scalajs.dom._
import quizleague.web.core._
import quizleague.web.site.team.TeamService
import quizleague.web.site.user.SiteUserService
import quizleague.web.store.Firestore
import quizleague.web.useredit.LoginCheckComponent.{facade, method}
import quizleague.web.useredit.TeamEditComponent

import scala.scalajs.js
import scala.scalajs.js.Dynamic.literal

@js.native
trait LoginPage extends VueRxComponent{
  var showAlert:Boolean
}

object LoginPage extends RouteComponent{

  override type facade = LoginPage
  
  val template = """
    <v-container>
    <v-layout column>
    <v-card>
      <v-card-text>
      <ql-named-text name="login-text"></ql-named-text>
      <v-text-field v-model.email="email" label="Enter your email address"></v-text-field><v-btn button v-on:click="login(email,$route.query.forward?$route.query.forward : '/home')" :disabled="!email">Submit</v-btn>
      <v-alert type="info" transition="scroll-y-transition" :value="showAlert">An email has been sent with login instructions.</v-alert>
     </v-card-text>
     </v-card>
    </v-layout >
    </v-container>
    """
  components(TeamEditComponent)

  data("showAlert", false)


   def login(c:facade, email:String, forward:String) = {

    LoginService.login(email, forward).subscribe(b => {c.showAlert = b})
  }

  data("email", null)
  method("login"){login _:js.ThisFunction}


  
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

object LoginCheckComponent extends RouteComponent {

  val template = """<v-layout align-center justify-center row fill-height style="font-size:72pt;opacity:0.6;">Please Wait...{{check($route.query.forward)}}</v-layout>"""

  method("check"){LoginService.check _ :js.ThisFunction}


}

object LoginFailedComponent extends RouteComponent {

  val template = """<v-layout >Login failed.  Please contact the webmaster.</v-layout>"""

}

//<v-dialog v-if="user" persistent v-model="profile" lazy max-width="40%" v-bind="dialogSize" >
//<v-card>
//<v-form>
//<v-card-title class="primary"><h5 class="display-1 white--text font-weight-light">Profile Settings</h5>
//</v-card-title>
//<v-card-text>
//<v-layout column>
//<v-text-field type="text" label="Handle" v-model="user.handle"></v-text-field>
//<v-text-field type="url" label="Avatar" v-model="user.avatar">
//<template slot="append" size="36">
//<v-avatar><img :src="user.avatar"></v-avatar>
//</template>
//</v-text-field>
//</v-layout>
//</v-card-text>
//<v-card-actions><v-btn @click="saveUser(user);profile=false">Save</v-btn><v-spacer></v-spacer><v-btn @click="profile=false">Cancel</v-btn></v-card-actions>
//</v-card>
//</v-form>
//</v-dialog>


