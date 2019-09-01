package quizleague.web.site.login

import java.util.regex.Pattern

import com.felstar.scalajs.vue.{VueRxComponent, VuetifyComponent}
import org.scalajs.dom.raw.{File, FileReader, UIEvent}
import org.scalajs.dom.window.alert
import quizleague.web.core._
import quizleague.web.model.SiteUser
import quizleague.web.site.NoSideMenu
import quizleague.web.site.team.TeamEditComponent
import quizleague.web.site.user.SiteUserService

import scala.scalajs.js
import scala.scalajs.js.Dynamic._
import scala.scalajs.js.UndefOr
import quizleague.web.util.Logging.log

@js.native
trait LoginPage extends VueRxComponent{
  var showAlert:Boolean
  var showProgress:Boolean
  var showFailure:Boolean
  var failureText:String
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
        <v-flex align-center style="padding-left:48%;"><v-progress-circular v-if="showProgress" indeterminate color="primary"></v-progress-circular></v-flex>
        <v-alert type="info" transition="scroll-y-transition" :value="showAlert">An email has been sent with login instructions.</v-alert>
        <v-alert type="error" transition="scroll-y-transition" :value="showFailure">{{failureText}}</v-alert>
       </v-card-text>
     </v-card>

    </v-layout >
    </v-container>
    """
  data("showAlert", false)
  data("showProgress", false)
  data("showFailure", false)
  data("failureText","")


   def login(c:facade, email:String, forward:String) = {

     c.showProgress = true
     LoginService.login(email, forward).subscribe(
       b => {c.showAlert = b;c.failureText = "You are not registered, or do not belong to a team.  Contact your team captain or the webmaster.";c.showFailure = !b;c.showProgress = false},
       e => {c.failureText = e.toString;c.showFailure = true;c.showProgress=false})
  }

  data("email", null)
  method("login"){login _:js.ThisFunction}


  
}

object LoginTitleComponent extends RouteComponent{
  val  template="""
    <v-toolbar
      color="amber darken-3"
      dark
      >
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


@js.native
trait ProfileEditComponent extends VueRxComponent{
 var user:SiteUser
}
object ProfileEditComponent extends RouteComponent with NoSideMenu with GridSizeComponentConfig{

  override type facade = ProfileEditComponent with LoginPage with VueRxComponent with VuetifyComponent

  val template="""
<v-container v-bind="gridSize" fluid>
  <v-layout column>
    <v-card class="mb-3">
      <v-form v-model="valid" ref="fm">
       <v-card-text>
        <v-layout column>
          <ql-named-text v-if="$route.query.first == 'true'" name="profile-first-time"></ql-named-text>
          <v-text-field prepend-icon="mdi-account" type="text" label="Handle" v-model="user.handle" :rules="[rules.required]" hint="This is how you'll be identified in chat messages." persistent-hint="true"></v-text-field>
          <v-file-input prepend-icon="mdi-account-circle" label="Avatar" placeholder="This will appear alongside your handle in chat messages"  :rules="[]" hint="Select a file if you wish to customise your avatar." :show-size="true" persistent-hint="true" v-on:change="upload">
          </v-file-input>

        </v-layout>
        <v-avatar><img :src="user.avatar" ></v-avatar>
      </v-card-text>
      <v-card-actions></v-card-actions>

      </v-form>
    </v-card>
    <v-layout row>
      <v-btn color="primary" text :disabled="!valid" @click="saveUser(user);forward($route.query.forward)"><v-icon left>mdi-content-save</v-icon>Save</v-btn>
      <v-flex grow><v-alert type="info" transition="scroll-y-transition" :value="showAlert">Profile Settings Saved.</v-alert></v-flex>
    </v-layout>
  </v-layout>
</v-container>
  """


  def uploadFiles(c:facade,file:File) = {
    val reader = new FileReader
    reader.readAsDataURL(file)
    reader.onload = (e:UIEvent) => {
      val data = reader.result
      if(data.toString.length <= 1000000){
        c.user.avatar = data.toString
      }
      else{
        alert("Avatar images must be less than 1Mb in size")
      }

    }
  }
  data("showAlert", false)
  data("valid",false)
  data("rules", literal(
    required=(value:UndefOr[String]) => if(value.filter(_ != null).exists(!_.isEmpty)) true else "Required"))
  method("saveUser")({(c:facade,user:SiteUser) => SiteUserService.save(user).subscribe(u => c.showAlert = true)}:js.ThisFunction)
  method("forward")({(c:facade, forward:UndefOr[String]) => forward.filter(_ != null).foreach(f => c.$router.push(f))}:js.ThisFunction)
  method("upload")({uploadFiles _}:js.ThisFunction)
  subscription("user")(c => LoginService.userProfile.filter(_ != null).map(_.siteUser))

}

object ProfileEditTitleComponent extends RouteComponent{
  val  template="""
    <v-toolbar
      color="amber darken-3"
      dark
      >
      <ql-title>Profile Settings</ql-title>
      <v-toolbar-title class="white--text" >
        Profile Settings
      </v-toolbar-title>
    </v-toolbar>"""
}




