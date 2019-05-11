package quizleague.web.site.login

import java.util.regex.Pattern

import com.felstar.scalajs.vue.{VueRxComponent, VuetifyComponent}
import firebase.Firebase
import firebase.auth.Auth
import quizleague.web.core._
import quizleague.web.model.SiteUser
import quizleague.web.site.NoSideMenu
import quizleague.web.site.SiteComponent.subscription
import quizleague.web.site.user.SiteUserService
import quizleague.web.useredit.TeamEditComponent

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import js.Dynamic._

@js.native
trait LoginPage extends VueRxComponent{
  var showAlert:Boolean
}

object LoginPage extends RouteComponent with NoSideMenu{

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

object LoginCheckComponent extends RouteComponent with NoSideMenu{

  val template = """<v-layout align-center justify-center row fill-height style="font-size:72pt;opacity:0.6;">Please Wait...{{check($route.query.forward)}}</v-layout>"""

  method("check"){LoginService.check _ :js.ThisFunction}


}

object LoginFailedComponent extends RouteComponent with NoSideMenu {

  val template = """<v-layout >Login failed.  Please contact the webmaster.</v-layout>"""

}



object ProfileEditComponent extends RouteComponent with NoSideMenu with GridSizeComponentConfig{

  val urlPattern = Pattern.compile("""https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)""")

  override type facade = LoginPage with VueRxComponent with VuetifyComponent

  val template="""
<v-container v-bind="gridSize" fluid>
  <v-layout column>
    <v-card class="mb-3">
      <v-form v-model="valid" ref="fm">
       <v-card-text>
        <v-layout column>
          <v-text-field type="text" label="Handle" v-model="user.handle" :rules=[rules.required]></v-text-field>
          <v-text-field type="url" label="Avatar" v-model="user.avatar" :rules=[rules.required,rules.url]>
            <template slot="append" size="36">
            <v-avatar><img :src="user.avatar"></v-avatar>
            </template>
          </v-text-field>

        </v-layout>
      </v-card-text>
      <v-card-actions><v-alert type="info" transition="scroll-y-transition" :value="showAlert">Profile Details Saved.</v-alert></v-card-actions>

      </v-form>
    </v-card>
    <v-layout row><v-btn primary @click="saveUser(user);forward($route.query.forward)"><v-icon left>mdi-content-save</v-icon>Save</v-btn></v-layout>
  </v-layout>
</v-container>

  """
  data("showAlert", false)
  data("valid",false)
  data("rules", literal(
    required=(value:UndefOr[String]) => if(value.filter(_ != null).exists(!_.isEmpty)) true else "Required",
    url = (value:String) => if(urlPattern.matcher(value).matches()) true else "Valid URL required"))
  method("saveUser")({(c:facade,user:SiteUser) => SiteUserService.save(user).subscribe(u => c.showAlert = true)}:js.ThisFunction)
  method("forward")({(c:facade, forward:UndefOr[String]) => forward.filter(_ != null).foreach(f => c.$router.push(f))}:js.ThisFunction)
  subscription("user")(c => LoginService.userProfile.map(_.siteUser))

}

object ProfileEditTitleComponent extends RouteComponent{
  val  template="""
    <v-toolbar
      color="amber darken-3"
      dark
      clipped-left>
      <ql-title>Profile Settings</ql-title>
      <v-toolbar-title class="white--text" >
        Profile Settings
      </v-toolbar-title>
    </v-toolbar>"""
}




