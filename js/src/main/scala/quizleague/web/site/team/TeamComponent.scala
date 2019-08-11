package quizleague.web.site.team

import quizleague.web.core.Component
import quizleague.web.core.DialogComponent
import quizleague.web.core.DialogComponentConfig
import quizleague.web.core.GridSizeComponentConfig
import quizleague.web.core.IdComponent
import quizleague.web.core.RouteComponent
import quizleague.web.model.ApplicationContext
import quizleague.web.model.Team
import quizleague.web.site.ApplicationContextService

import scala.scalajs.js
import quizleague.web.site.fixtures.FixtureService
import quizleague.web.util.Clipboard
import org.scalajs.dom
import quizleague.web.site.SideMenu
import quizleague.web.site.login.LoginService

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
            <v-container v-if="team && seasonId" v-bind="gridSize" fluid>
              <v-layout column>

           <v-flex><ql-text :id="team.text.id"></ql-text></v-flex>
            <v-flex><standings :id="team.id"></standings></v-flex>
            <v-flex><team-results :id="team.id" :seasonId="seasonId"></team-results>
            </v-flex>      
            <v-flex><team-fixtures :id="team.id" :seasonId="seasonId"></team-fixtures>
            </v-flex>
          </v-layout>
          </v-container>"""
  props("id")
  subscription("team","id")(v => TeamService.get(v.id))
  subscription("seasonId")(c => ApplicationContextService.get.map(_.currentSeason.id))

  components(TeamStandings,TeamResults,TeamFixtures)
}

object TeamTitleComponent extends RouteComponent {
  override val template = """<ql-team-title :id="$route.params.id"></ql-team-title>"""
}

@js.native
trait TeamTitle extends IdComponent{
  var contact:Boolean
}

object TeamStandings extends Component {
  
  type facade = IdComponent
  
  val name = "standings"
  val template ="""        
      <v-card v-if="standings && standings.length > 0">
        <v-card-title primary-title><h3 class="headline mb-0">Standings</h3></v-card-title>
        <v-card-text>
          <table>
            <standing v-for="s in standings" :standing="s"></standing>
          </table>
        </v-card-text>
      </v-card>"""
  props("id")
  subscription("standings","id")(c => TeamService.standings(c.id))
  
  components(StandingComponent)
}

object StandingComponent extends Component {
  val name = "standing"
  val template = """
    <tr v-if="standing.name">
      <td>{{standing.name}}</td><td> : </td><td>{{standing.standing}}</td>
    </tr>
    <tr v-else>
      <td colspan="3">{{standing.standing}}</td>
    </tr>
    """
  prop("standing")
}

object TeamResults extends Component{
  val name = "team-results"
  val template = """
    <v-card>
      <v-card-title primary-title><h3 class="headline mb-0">Results</h3></v-card-title>
      <v-card-title >Last few results</v-card-title>
      <v-card-text>
        <ql-fixtures-simple :fixtures="results(id, seasonId)" :inlineDetails="true"></ql-fixtures-simple>
      </v-card-text>
      <v-card-actions>
        <v-btn text :to="id + '/results'" color="primary">Show All</v-btn>
        <v-btn text :to="id + '/stats'"><v-icon left>insert_chart</v-icon>Graphs & Stats</v-btn>
      </v-card-actions>
    </v-card>"""
  
  props("id","seasonId")
  method("results")((teamId:String, seasonId:String) => FixtureService.teamResults(teamId, seasonId, 5)) 

}

object TeamFixtures extends Component{
  val name = "team-fixtures"
  val template = """
    <v-card >
      <v-card-title primary-title><h3 class="headline mb-0">Fixtures</h3></v-card-title>
      <v-card-title >Next few fixtures</v-card-title>
      <v-card-text>
        <ql-fixtures-simple :fixtures="fixtures(id, seasonId)" :inlineDetails="true"></ql-fixtures-simple>
      </v-card-text>
      <v-card-actions>
        <v-btn text :to="id + '/fixtures'" color="primary">Show All</v-btn>
        <v-menu offset-y>
          <template v-slot:activator="{ on }">
          <v-btn text v-on="on" ><v-icon left>mdi-calendar</v-icon>Calendar</v-btn>
          </template>
           <v-list>
            <v-list-item v-on:click="copy(id)">
              <v-list-item-action v-on:click="copy(id)"><v-icon left>content_copy</v-icon></v-list-item-action>
              <v-list-item-content ><v-list-item-title>Copy Calendar URL</v-list-item-title></v-list-item-content>
            </v-list-item>
            <v-list-item :href="'calendar/team/' + id + '/calendar.ics'" target="_blank">
              <v-list-item-action ><v-icon left>mdi-download</v-icon></v-list-item-action>
              <v-list-item-content><v-list-item-title>Download Calendar File</v-list-item-title></v-list-item-content>
            </v-list-item>
          </v-list>

        </v-menu>              
      </v-card-actions>
    </v-card>"""
  props("id","seasonId")
  method("fixtures")((teamId:String, seasonId:String) => FixtureService.teamFixturesForSeason(teamId,seasonId,5))
  method("copy")((teamId:String) => Clipboard.copy(s"${dom.document.location.origin}/calendar/team/$teamId"))

}

object TeamTitle extends Component {
  
  type facade = TeamTitle
  
  override val name = "ql-team-title"
  override val template = """
    <v-toolbar      
      color="amber darken-3"
      dark
      v-if="team">
      <ql-title>{{team.name}}</ql-title>
      <v-toolbar-title class="white--text" >
      <ql-r-team-name :team="team"></ql-r-team-name>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-tooltip top><template v-slot:activator="{ on }"><v-btn icon v-on="on" v-on:click="contact=true"><v-icon>email</v-icon></v-btn></template><span>Contact Us</span></v-tooltip>
      <v-tooltip top><template v-slot:activator="{ on }"><v-btn icon :to="'/venue/' + team.venue.id"><v-icon>location_on</v-icon></v-btn></template><span>Venue</span></v-tooltip>
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
          <v-dialog v-model="show" max-width="60%" v-bind="dialogSize" persistent>
            <v-card>
              <v-card-title>Contact {{team.name}}</v-card-title>
              <v-card-text>
                <v-form v-model="valid">
                <v-container>
                  <v-layout column>
                    <v-text-field required label="Your email address" v-model="email" type="email" :rules="[required('Your email address'), isEmail('Your email address')]"></v-text-field>
                    <v-textarea label="Message" v-model="text" outline auto-grow :rules="[required('Message')]" required></v-textarea>
                  </v-layout>
                </v-container>
                </v-form>
              </v-card-text>
              <v-card-actions><v-spacer></v-spacer><v-btn text v-on:click="close"><v-icon left>cancel</v-icon>Cancel</v-btn><v-btn text color="primary" :disabled="!valid" v-on:click="submit">Send<v-icon right>send</v-icon></v-btn></v-card-actions>
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

object TeamMenuComponent extends RouteComponent with SideMenu{
   override val template = """
     <ql-side-menu title="Teams" icon="people">
       <v-list-item to="/team/start">
        <v-list-item-content><v-list-item-title>Start a team</v-list-item-title></v-list-item-content>
      </v-list-item>
      <v-list-item v-if="user" to="/team/edit">
          <v-list-item-content><v-list-item-title>Edit Team Details</v-list-item-title></v-list-item-content>
      </v-list-item>
      <v-divider></v-divider>
       <v-list-item v-if="teams" v-for="team in teams" :key="team.id" :to="'/team/' + team.id">
          <v-list-item-content><v-list-item-title v-text="team.name"></v-list-item-title></v-list-item-content>
       </v-list-item>
     </ql-side-menu>
     """
   subscription("teams")(c => TeamService.list().map(_.sortBy(_.shortName)))
  subscription("user")(c => LoginService.userProfile)
}

