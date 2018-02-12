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
  var activeTab:String
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
      <div>
      <v-tabs  :scrollable="true" ripple v-model="activeTab" >
        <v-tabs-bar ripple @click.native="haltTabs()">
        <v-tabs-slider color="yellow"></v-tabs-slider>
          <v-tabs-item href="#league">Tables</v-tabs-item>
          <v-tabs-item href="#results">Results</v-tabs-item>
          <v-tabs-item href="#fixtures">Fixtures</v-tabs-item>
        </v-tabs-bar>

        <v-tabs-items>
          <v-tabs-content id="league">
           <ql-home-page-table style="min-width:300px" :seasonId="appData.currentSeason.id"></ql-home-page-table>
          </v-tabs-content>
          <v-tabs-content id="results">
            <ql-latest-results style="min-width:300px" :seasonId="appData.currentSeason.id"></ql-latest-results>
          </v-tabs-content>
          <v-tabs-content id="fixtures">
            <ql-next-fixtures style="min-width:300px" :seasonId="appData.currentSeason.id"></ql-next-fixtures></v-carousel-item>
          </v-tabs-content>
        </v-tabs-items>
      </v-tabs>
      </div>  

      <!--v-carousel light style="min-height:35em"  :cycle="true" >
          <v-carousel-item src="" style="align-item:start;">
          <v-container fluid>
          <v-layout column>
            <ql-home-page-table :seasonId="appData.currentSeason.id"></ql-home-page-table>
          <v-layout>
          </v-container>
          </v-carousel-item>
          <v-carousel-item src="">
          <v-container>
              <ql-latest-results :seasonId="appData.currentSeason.id"></ql-latest-results>
          <v-container>
          </v-carousel-item>
           <v-carousel-item src="">
          <v-container>
              <ql-next-fixtures :seasonId="appData.currentSeason.id"></ql-next-fixtures>
          </v-container>
          </v-carousel-item>
        </v-carousel-->
      </v-flex>
      <v-flex offset-xs0 offset-md1 xs12>
        <ql-named-text name="front-page"></ql-named-text>
        <ql-text v-if="async(appData.currentSeason).id" :id="async(appData.currentSeason).text.id"></ql-text>
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
  data("activeTab", null)
  data("tabsHandle", null)

  subscription("appData")(c => ApplicationContextService.get())
  
  def nextTab(c:facade) = c.activeTab = tabs((tabs.indexOf(c.activeTab) + 1) % tabs.length)
  def haltTabs(c:facade) = clearInterval(c.tabsHandle)

  def align(c: facade) = js.Dictionary("column" -> c.$vuetify.breakpoint.smAndDown)
  computed("align")({ align _ }: js.ThisFunction)
  method("haltTabs")({haltTabs _}:js.ThisFunction)

  override val mounted = ({(c:facade) => {
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
   <v-card flat>
     <v-card-title primary-title><h3 class="headline mb-0">Next Fixtures</h3></v-card-title>
     <v-card-text v-if="fixtures">
        <div v-for="f in fixtures" :key="f.id">
        <h4>{{f.description}} {{f.date | date("d MMM yyyy")}}</h4>
        <ql-fixtures-simple :fixtures="f.fixtures | combine"></ql-fixtures-simple>
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
   <v-card flat>
     <v-card-title primary-title><h3 class="headline mb-0">Latest Results</h3></v-card-title>
     <v-card-text v-if="fixtures">
        <div v-for="f in fixtures" :key="f.id">
        <h4>{{f.description}} {{f.date | date("d MMM yyyy")}}</h4>
        <ql-fixtures-simple :fixtures="f.fixtures | combine"></ql-fixtures-simple>
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
  
  override val template ="""
            <v-card flat v-if="tables">
              <v-card-title primary-title><h3 class="headline mb-0">League Table</h3></v-card-title>
              <v-card-text grow>
              <v-container fluid>
                <v-layout row v-bind="justify">
                  <ql-league-table v-for="table in tables"  :key="table.id" :id="table.id"></ql-league-table>
                </v-layout>
              </v-container>
              </v-card-text>
            </v-card>"""
  
  props("seasonId")
  subscription("tables", "seasonId")(c => LeagueTableService.leagueTables(c.seasonId))
  
  def justify(c: facade) = js.Dictionary("justify-space-around" -> c.$vuetify.breakpoint.smAndUp)
  computed("justify")({ justify _ }: js.ThisFunction)
}