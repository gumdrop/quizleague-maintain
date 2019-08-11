package quizleague.web.site.results

import quizleague.web.core._
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.core.Component
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.season.SeasonIdComponent
import quizleague.web.core.GridSizeComponentConfig
import com.felstar.scalajs.vue.VuetifyComponent
import quizleague.web.site.season.SeasonFormatComponent
import quizleague.web.site.SideMenu



object AllResultsPage extends RouteComponent{
  val template = """<div>
                      <ql-all-results></ql-all-results>
                    </div>"""
  
  components(AllResultsComponent)
}


object AllResultsComponent extends Component with GridSizeComponentConfig{
  val name = "ql-all-results"
  type facade = SeasonIdComponent with VuetifyComponent
  val template = """
    <v-container v-bind="gridSize" fluid>
    <v-layout column  v-if="fixtures">
      <v-flex v-for="fixs in fixtures" :key="fixs.id">
        <v-card>
          <v-card-title primary-title><h3 class="headline mb-0">{{fixs.date | date('d MMM yyyy')}} - {{fixs.parentDescription}} {{fixs.description}}</h3></v-card-title>
          <v-card-text>
            <ql-fixtures-simple :fixtures="fixs.fixtures | combine"></ql-fixtures-simple>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    </v-container>"""
  
  subscription("fixtures")(c => ResultsViewService.results)
  
}

object AllResultsTitleComponent extends RouteComponent with SeasonFormatComponent{
  val template = """<v-toolbar      
      color="red darken-3"
      dark
      dense
      >
      <ql-title v-if="s">All Results {{formatSeason(s)}}</ql-title>
      <v-toolbar-title class="white--text" >
        All Results
       </v-toolbar-title>
       <span style="padding-left:2em;"></span>
      <v-toolbar-items>
      <ql-season-select :season="season" :inline="true"></ql-season-select>
      </v-toolbar-items>
    </v-toolbar>"""
  
  data("season", ResultsViewService.season)
  subscription("s")(c => ResultsViewService.season)
}