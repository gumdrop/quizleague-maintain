package quizleague.web.site.home



import quizleague.web.core._
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.fixtures.FixturesService
import com.felstar.scalajs.vue.VueComponent
import quizleague.web.model.Season
import scalajs.js
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.site.season.SeasonService
import quizleague.web.site.leaguetable.LeagueTableService
import com.felstar.scalajs.vue.VuetifyComponent
import quizleague.web.site._
import quizleague.web.core.GridSizeComponentConfig



object HomeComponent extends RouteComponent with NoSideMenu with GridSizeComponentConfig{

  type facade = VueRxComponent with VuetifyComponent
  
  subscription("appData")(c => ApplicationContextService.get()
  )
  override val template="""
   <v-container v-bind="gridSize" v-if="appData">
     <v-layout v-bind="align">
      <v-flex xs12 smAndUp5>
      <!--div>
      <v-tabs grow :scrollable="false">
        <v-tabs-bar ripple>
        <v-tabs-slider color="yellow"></v-tabs-slider>
          <v-tabs-item href="#league">League Tables</v-tabs-item>
          <v-tabs-item href="#results">Latest Results</v-tabs-item>
          <v-tabs-item href="#fixtures">Next Fixtures</v-tabs-item>
        </v-tabs-bar>

        <v-tabs-items>
          <v-tabs-content id="league">
           <ql-home-page-table style="min-width:400px" :seasonId="appData.currentSeason.id"></ql-home-page-table>
          </v-tabs-content>
          <v-tabs-content id="results">
            <ql-latest-results style="min-width:400px" :seasonId="appData.currentSeason.id"></ql-latest-results>
          </v-tabs-content>
          <v-tabs-content id="fixtures">
            <ql-next-fixtures style="min-width:400px" :seasonId="appData.currentSeason.id"></ql-next-fixtures></v-carousel-item>
          </v-tabs-content>
        </v-tabs-items>
      </v-tabs>
      </div-->  

      <v-carousel light style="min-height:35em"  :cycle="true">
          <v-carousel-item src="" style="align-item:start;">
            <ql-home-page-table :seasonId="appData.currentSeason.id"></ql-home-page-table>
          </v-carousel-item>
          <v-carousel-item src=""><ql-latest-results :seasonId="appData.currentSeason.id"></ql-latest-results>
          </v-carousel-item>
          <v-carousel-item src=""><ql-next-fixtures :seasonId="appData.currentSeason.id"></ql-next-fixtures></v-carousel-item>
        </v-carousel>
      </v-flex>
      <v-flex>
        <ql-named-text name="front-page"></ql-named-text>
        <ql-text v-if="async(appData.currentSeason).id" :id="async(appData.currentSeason).text.id"></ql-text>
      </v-flex>
    </v-layout>
  </v-container>
"""
    components(HomePageLeagueTable, NextFixturesComponent,LatestResultsComponent)
    def align(c:facade) = js.Dictionary("column" -> c.$vuetify.breakpoint.smAndDown)
    computed("align")({align _}:js.ThisFunction)

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
        <ql-results-simple :results="f.fixtures | combine"></ql-results-simple>
        </div>
     </v-card-text>
   </v-card>

"""
  
  props("seasonId")
  subscription("fixtures", "seasonId")(c => FixturesService.latestResults(c.seasonId))
}



@js.native
trait HomePageLeagueTable extends VueComponent with VueRxComponent{
  val seasonId:String
}


object HomePageLeagueTable extends Component{
  
  type facade = HomePageLeagueTable
  
  override val name = "ql-home-page-table"
  
  override val template ="""
            <v-card v-if="tables">
              <v-card-title primary-title><h3 class="headline mb-0">League Table</h3></v-card-title>
              <v-card-text>
              <ql-league-table v-for="table in tables"  :key="table.id" :id="table.id"></ql-league-table>
              </v-card-text>
            </v-card>"""
  
  props("seasonId")
  subscription("tables", "seasonId")(c => LeagueTableService.leagueTables(c.seasonId))
}