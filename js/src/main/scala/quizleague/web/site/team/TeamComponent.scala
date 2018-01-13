package quizleague.web.site.team

import com.felstar.scalajs.vue._
import scalajs.js
import js.JSConverters
import quizleague.web.core.RouteComponent
import quizleague.web.core.Component
import quizleague.web.site.text.TextService
import quizleague.web.core._
import quizleague.web.model.Team
import quizleague.web.site.ApplicationContextService
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.model.ApplicationContext
import quizleague.web.site.fixtures.FixtureService
import quizleague.web.site._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.core.IdComponent
import com.felstar.scalajs.vue.VuetifyComponent
import quizleague.web.core.DialogComponentConfig

object TeamPage extends RouteComponent{
  override val template = """<ql-team :id="$route.params.id"></ql-team>"""
}

@js.native
trait TeamComponent extends IdComponent{
  val appConfig:ApplicationContext
}
object TeamComponent extends Component with GridSizeComponentConfig{

  type facade = IdComponent
  
  override val name = "ql-team"
  override val template = """
            <v-container v-if="team && appConfig" v-bind="gridSize" fluid>
              <v-layout column>

           <v-flex><ql-text :id="team.text.id"></ql-text></v-flex>

            <v-flex><v-card >
              <v-card-title primary-title><h3 class="headline mb-0">Results</h3></v-card-title>
              <v-card-title >Last few results</v-card-title>
              <v-card-text>
                <ql-fixtures-simple :fixtures="results(id, appConfig.currentSeason.id)" :inlineDetails="true"></ql-fixtures-simple>
              </v-card-text>
              <v-card-actions>
                <v-btn flat :to="id + '/results'" color="primary">Show All</v-btn>
                <v-btn flat><v-icon>insert_chart</v-icon>Graphs & Stats</v-btn>
              </v-card-actions>
            </v-card>
            </v-flex>      
            <v-flex><v-card >
              <v-card-title primary-title><h3 class="headline mb-0">Fixtures</h3></v-card-title>
              <v-card-title >Next few fixtures</v-card-title>
              <v-card-text>
                <ql-fixtures-simple :fixtures="fixtures(id, appConfig.currentSeason.id)" :inlineDetails="true"></ql-fixtures-simple>
              </v-card-text>
              <v-card-actions>
                <v-btn flat :to="id + '/fixtures'" color="primary">Show All</v-btn>
                <v-btn flat ><v-icon>content_copy</v-icon>Calendar URL</v-btn>
                <v-btn flat><v-icon>file_download</v-icon>Download Calendar</v-btn>
              </v-card-actions>
            </v-card>
            </v-flex>
          </v-layout>
          </v-container>"""
  props("id")
  subscription("team","id")(v => TeamService.get(v.id))
  subscription("appConfig")(c => ApplicationContextService.get)
  method("fixtures")((teamId:String, seasonId:String) => FixtureService.teamFixtures(teamId,seasonId,5))
  method("results")((teamId:String, seasonId:String) => FixtureService.teamResults(teamId,seasonId,5))  

}

object TeamTitleComponent extends RouteComponent {
  override val template = """<ql-team-title :id="$route.params.id"></ql-team-title>"""
}

@js.native
trait TeamTitle extends IdComponent{
  var contact:Boolean
}

object TeamTitle extends Component {
  
  type facade = TeamTitle
  
  override val name = "ql-team-title"
  override val template = """
    <v-toolbar      
      color="amber darken-3"
      dark
      clipped-left
      v-if="team">
      <v-toolbar-title class="white--text" >
        {{team.name}}
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-tooltip top><v-btn icon slot="activator" v-on:click="contact=true"><v-icon>email</v-icon></v-btn><span>Contact Us</span></v-tooltip>
      <v-tooltip top><v-btn icon :to="'/venue/' + team.venue.id" slot="activator"><v-icon>location_on</v-icon></v-btn><span>Venue</span></v-tooltip>
      <ql-team-contact-dialog :show="contact" :team="team" v-on:show="handleShow"></ql-team-contact-dialog> 
    </v-toolbar>"""
   components(ContactDialog)
   props("id")
   data("contact",false)
   subscription("team","id")(v => TeamService.get(v.id))
   method("handleShow")({(c:facade,show:Boolean) => c.contact = show}:js.ThisFunction)

}

@js.native
trait ContactDialog extends DialogComponent{
  var email:String
  var text:String
  val team:Team
  var show:Boolean
}
object ContactDialog extends Component with DialogComponentConfig{
  
  import quizleague.web.util.validation.Functions._
  
  type facade = ContactDialog
  val name = "ql-team-contact-dialog"
  val template = """
          <v-dialog v-model="show" max-width="60%" v-bind="dialogSize" lazy persistent>
            <v-card>
              <v-card-title>Contact {{team.name}}</v-card-title>
              <v-card-text>
                <v-form v-model="valid">
                <v-container>
                  <v-layout column>
                    <v-text-field required label="Your email address" v-model="email" type="email" :rules="[required('Your email address'), isEmail('Your email address')]"></v-text-field>
                    <v-text-field label="Message" v-model="text" textarea auto-grow :rules="[required('Message')]" required></v-text-field>
                  </v-layout>
                </v-container>
                </v-form>
              </v-card-text>
              </v-card-actions><v-btn flat v-on:click="close"><v-icon left>cancel</v-icon>Cancel</v-btn><v-btn flat color="primary" :disabled="!valid" v-on:click="submit">Send<v-icon right>send</v-icon></v-btn></v-card-actions>
            </v-card>
         </v-dialog>"""
  
  def submit(c:facade){
    TeamService.sendEmailToTeam(c.email, c.text, c.team)
    c.show = false
    c.text=""
    }
  
  
  props("team")
  data("email","")
  data("text","")
  data("valid",false)
  method("submit")({submit _}:js.ThisFunction)
  method("required")(required _)
  method("isEmail")(isEmail _)

}

object TeamMenuComponent extends RouteComponent {
  
  override val template = """
     <v-list dense v-if="teams">
     <v-list-group no-action :value="true">
            <v-list-tile slot="item" @click="">
              <v-list-tile-action>
                <v-icon>people</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>Teams</v-list-tile-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-icon>keyboard_arrow_down</v-icon>
              </v-list-tile-action>
            </v-list-tile>
       <v-list-tile v-for="team in sort(teams) " :key="team.id">
        <v-btn :to="'/team/' + team.id" flat >{{team.name}}</v-btn>
       </v-list-tile>
    </v-list-group>
     </v-list>"""
    subscription("teams")(c => TeamService.list)
    method("sort")((teams:js.Array[Team]) => teams.sortBy(_.shortName)
    )
}