package quizleague.web.site.home



import quizleague.web.core._
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.fixtures.FixturesService
import com.felstar.scalajs.vue.VueComponent
import quizleague.web.model.Season
import scalajs.js
import js.timers._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.site.season.SeasonService
import quizleague.web.site.leaguetable.LeagueTableService
import com.felstar.scalajs.vue.VuetifyComponent
import quizleague.web.site._
import quizleague.web.core.GridSizeComponentConfig

@js.native
trait HomeComponent extends VueRxComponent with VuetifyComponent{
  var sponsorMessage:Boolean
  var activeTab:Int
  var tabsHandle:SetIntervalHandle
}

object HomeComponent extends RouteComponent with NoSideMenu with GridSizeComponentConfig{

  type facade = HomeComponent
  
  val tabs = js.Array("league","results","fixtures")
  

  override val template="""
   <v-container v-bind="gridSize" v-if="appData">
     <ql-title>Home</ql-title>
     <v-layout v-bind="align">
      <v-flex xs12 smAndUp5>
        <v-card>
          <v-tabs ripple :value="activeTab" slider-color="yellow" centered @click.native="haltTabs()">
              <v-tab key="1">Tables</v-tab>
              <v-tab key="2">Results</v-tab>
              <v-tab key="3">Fixtures</v-tab>
              <v-tab-item key="1">
               <ql-home-page-table style="min-width:300px" :seasonId="appData.currentSeason.id"></ql-home-page-table>
              </v-tab-item>
              <v-tab-item key="2">
                <ql-latest-results style="min-width:300px" :seasonId="appData.currentSeason.id"></ql-latest-results>
              </v-tab-item>
              <v-tab-item key="3">
                <ql-next-fixtures style="min-width:300px" :seasonId="appData.currentSeason.id"></ql-next-fixtures></v-carousel-item>
              </v-tab-item>
          </v-tabs>
        </v-card>
      </v-flex>
      <v-flex offset-xs0 offset-md1 xs12>
        <v-layout column>
          <ql-text-box>
            <ql-named-text name="front-page"></ql-named-text>
            <ql-text v-if="async(appData.currentSeason).id" :id="async(appData.currentSeason).text.id"></ql-text>
          </ql-text-box>

        <ql-hot-chats></ql-hot-chats>
        </v-layout>
      </v-flex>
    </v-layout>
     <v-snackbar
      timeout="3000"
      :multi-line="true"
      v-model="sponsorMessage"
    >
      <ql-named-text name="sponsor-message"></ql-named-text
     </v-snackbar>
  </v-container>
"""
  components(HomePageLeagueTable, NextFixturesComponent, LatestResultsComponent)
  data("sponsorMessage", false)
  data("activeTab", 0)
  data("tabsHandle", null)

  subscription("appData")(c => ApplicationContextService.get())
  
  def nextTab(c:facade) = c.activeTab = ((c.activeTab + 1) % tabs.length)
  def haltTabs(c:facade) = clearInterval(c.tabsHandle)

  def align(c: facade) = js.Dictionary("column" -> c.$vuetify.breakpoint.smAndDown)
  computed("align")({ align _ }: js.ThisFunction)
  method("haltTabs")({haltTabs _}:js.ThisFunction)

  override val mounted = ({(c:facade) => {
    super.mounted.call(c);
    setTimeout(5000)(c.sponsorMessage = true);
    c.tabsHandle = setInterval(5000)(nextTab(c))
  }}:js.ThisFunction)
  
  override val beforeDestroy = {haltTabs _}:js.ThisFunction
}

@js.native
trait NextFixturesComponent extends VueRxComponent{
  val seasonId:String
}


object NextFixturesComponent extends Component{
  type facade = NextFixturesComponent
  
  val name = "ql-next-fixtures"
  val template = 
    
    """
   <v-card text>
     <v-card-title primary-title><h3 class="headline mb-0">Next Fixtures</h3></v-card-title>
     <v-card-text v-if="fixtures">
        <div v-for="f in fixtures" :key="f.id" style="margin-bottom:1em;">
        <h4>{{f.parentDescription}} {{f.description}} {{f.date | date("d MMM yyyy")}}</h4>
        <ql-fixtures-simple :fixtures="f.fixture"></ql-fixtures-simple>
        </div>
     </v-card-text>
   </v-card>

"""
  

  
  props("seasonId")
  subscription("fixtures", "seasonId")(c => FixturesService.nextFixtures(c.seasonId))

}

object LatestResultsComponent extends Component{
  type facade = NextFixturesComponent
  
  val name = "ql-latest-results"
  val template = 
    
    """
   <v-card text>
     <v-card-title primary-title><h3 class="headline mb-0">Latest Results</h3></v-card-title>
     <v-card-text v-if="fixtures">
        <div v-for="f in fixtures" :key="f.id" style="margin-bottom:1em;">
        <h4>{{f.parentDescription}} {{f.description}} {{f.date | date("d MMM yyyy")}}</h4>
        <ql-fixtures-simple :fixtures="f.fixture"></ql-fixtures-simple>
        </div>
     </v-card-text>
   </v-card>

"""

  props("seasonId")
  subscription("fixtures", "seasonId")(c => FixturesService.latestResults(c.seasonId))
}



@js.native
trait HomePageLeagueTable extends VueRxComponent with VuetifyComponent{
  val seasonId:String
}


object HomePageLeagueTable extends Component{
  
  type facade = HomePageLeagueTable
  
  override val name = "ql-home-page-table"
  
  override val template =s"""
            <v-card text v-if="tables">
              <v-card-title primary-title><h3 class="headline mb-0">League Table</h3></v-card-title>
              <v-card-text >
              <v-container fluid>
                <v-layout column v-bind:class="justify">
                  <v-layout row v-for="table in tables"  :key="table">
                   <ql-league-table  :keyval="table" class="mb-3"></ql-league-table>
                  </v-layout>
                </v-layout>
              </v-container>
              </v-card-text>
            </v-card>"""
  import quizleague.web.util.Logging.log
  props("seasonId")
  subscription("tables", "seasonId")(c => LeagueTableService.leagueTables(c.seasonId).map(_.map(_.key)))
  
  def justify(c: facade) = $("justify-space-around" -> "$vuetify.breakpoint.smAndUp")
  computed("justify")({ justify _ }: js.ThisFunction)
}