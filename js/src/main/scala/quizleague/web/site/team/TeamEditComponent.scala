package quizleague.web.site.team

import java.util.regex.Pattern

import scala.scalajs.js
import com.felstar.scalajs.vue.VueRxComponent
import firebase.Firebase
import firebase.auth._
import quizleague.web.core._
import quizleague.web.maintain.user.UserService
import quizleague.web.model.{Team, Text, User}
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.team.{TeamService => Service}
import quizleague.web.util.component.SelectUtils

import js.Dynamic.literal
import scala.scalajs.js.UndefOr
import org.scalajs.dom._
import quizleague.web.site.login.LoginService
import rxscalajs.Observable

object TeamEditPage extends RouteComponent{
  
  val template = """
    <ql-team-edit v-if="user" :id="user.team.id"></ql-team-edit>
    
    """
  components(TeamEditComponent)
  subscription("user")(c => LoginService.userProfile)

}


@js.native
trait TeamEditComponent extends IdComponent{
  var text:Text
  val team:Team
  var user:User
  var dialog:Boolean
  var success:Boolean
  var failure:Boolean
}
object TeamEditComponent extends Component with GridSizeComponentConfig{
  
  type facade = TeamEditComponent
  val name = "ql-team-edit"   
  val template=s"""
      <v-container v-bind="gridSize" fluid>
        <v-layout column v-if="team">
          <v-form v-model="valid" ref="fm">
            <v-card class="mb-3">
              <v-card-title>Names</v-card-title>
              <v-card-text>
            <v-text-field label="Name" v-model="team.name" length="20" :rules=[rules.required]></v-text-field>
            <v-text-field label="Short Name" v-model="team.shortName" length="10" :rules=[rules.required]></v-text-field>
              </v-card-text>
            </v-card>
            <v-card class="mb-3">
            <v-card-title>Team Members</v-card-title>
              <v-card-text>
                <v-btn text color="primary" @click="newUser()" dark><v-icon left>mdi-account-plus</v-icon>Add User</v-btn>
                <v-dialog v-model="dialog" persistent max-width="600px">

                  <v-card >
                    <v-card-title>
                      <span class="headline"><v-icon>mdi-account-plus</v-icon>&nbsp;New User</span>
                    </v-card-title>
                    <v-card-text v-if="user">
                      <v-container grid-list-md>
                        <v-layout column>
                          <v-flex xs12 sm6 md4>
                            <v-text-field prepend-icon="mdi-account" label="Name" required :rules=[rules.required] v-model="user.name"></v-text-field>
                          </v-flex>
                          <v-flex xs12 sm6 md4>
                            <v-text-field prepend-icon="mdi-email" label="Email" type="email" required :rules=[rules.required,rules.email] v-model="user.email"></v-text-field>
                          </v-flex>

                        </v-layout>
                      </v-container>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="blue darken-1" text @click="dialog = false"><v-icon left>mdi-close-circle</v-icon>Cancel</v-btn>
                      <v-btn color="blue darken-1" text @click="dialog = false;addUser()" :disabled="!valid"><v-icon left>mdi-account-plus</v-icon>Add</v-btn>
                    </v-card-actions>
                  </v-card>
               </v-dialog>
               <v-flex>
                <v-icon color="primary">mdi-account-multiple</v-icon>&nbsp;<v-chip v-for="usr in team.users" close @input="removeUser(usr.id)">{{async(usr).name}}</v-chip>
               </v-flex>
             </v-card-text>
             </v-card>
            <v-card class="mb-3">
              <v-card-title>Rubric</v-card-title>
              <v-card-text>
               <quill-editor v-if="text" v-model="text.text"></quill-editor>
              </v-card-text>
            </v-card>
            <v-layout row>
              <v-btn button text color="primary" v-on:click="submit()" :disabled="!valid"><v-icon left>mdi-content-save</v-icon>Save</v-btn>
              <v-flex grow><v-alert type="info" :icon="false" outlined border="left" text transition="scroll-y-transition" :value="success">Team details saved</v-alert></v-flex>
            </v-layout>
        </v-layout>
       </v-container>"""


  val emailPattern =  """^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$"""


  props("id")
  data("dialog", false)
  data("valid",false)
  data("user", null)
  data("success", false)
  data("rules", literal(
    required=(value:UndefOr[String]) => if(value.filter(_ != null).exists(!_.isEmpty)) true else "Required",
    email = (value:String) => if(Pattern.matches(emailPattern, value)) true else "Valid email required"))
  subscription("team","id")(v => Service.get(v.id))
  subscription("text", "id")(c => Service.get(c.id).flatMap(team => TextService.get(team.text.id)))

  method("newUser")({c:facade => {c.user = UserService.instance();c.dialog = true}}:js.ThisFunction)

  method("addUser")({c:facade => {
    UserService.userForEmail(c.user.email).subscribe { x =>
      val user = x.getOrElse(c.user)

      if(!c.team.users.exists(_.id == user.id)){
        UserService.save(user)
        c.team.users += UserService.getRO(user.id)
      }
    }

  }}:js.ThisFunction)

  method("removeUser")({(c:facade, userID:String) => {
        c.team.users -= UserService.getRO(userID)

  }}:js.ThisFunction)

  method("submit")({c:facade => {
    Service.save(c.team).combineLatest(TextService.save(c.text)).subscribe(x => c.success = true, e => c.failure = true, ()=> c.success = true)
  }}:js.ThisFunction)


}

