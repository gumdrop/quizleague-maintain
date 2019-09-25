package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.core.Component
import quizleague.web.site.fixtures.FixtureService
import quizleague.web.core.IdComponent
import scalajs.js
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.season.SeasonIdComponent
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig

object TeamFixturesPage extends RouteComponent with GridSizeComponentConfig{
  val template = """
      <v-container v-if="appData" v-bind="gridSize" fluid>
        <v-layout column>
          <v-flex><v-card>
            <v-card-text>
              <ql-all-team-fixtures v-if="appData" :id="$route.params.id" :seasonId="appData.currentSeason.id"></ql-all-team-fixtures>
            </v-card-text>
          </v-card></v-flex>
        </v-layout>
      </v-container>
"""
  subscription("appData")(c => ApplicationContextService.get)
  
  components(TeamFixturesComponent)
}

  
object TeamFixturesComponent extends Component {
  
  type facade = SeasonIdComponent with IdComponent
  val name = "ql-all-team-fixtures"
  val template = """<ql-fixtures-simple :fixtures="fixtures(id,seasonId)" :inlineDetails="true"></ql-fixtures-simple>"""
  method("fixtures")((teamId:String,seasonId:String) => FixtureService.teamFixturesForSeason(teamId,seasonId))
  props("id","seasonId")
  
}

object TeamFixturesTitle extends RouteComponent{
  val template = """<fixtures-title :id="$route.params.id"></fixtures-title>"""
 components(TeamFixturesTitleComponent)
}

object TeamFixturesTitleComponent extends Component{
  type facade = IdComponent
  
  val name = "fixtures-title"
  val template = """<v-toolbar      
      color="amber lighten-3"
      dense
      class="subtitle-background"
      v-if="team">
     <ql-title>{{team.name}} : Fixtures</ql-title>
      <v-toolbar-title v-if="team">
        <ql-r-team-name :team="team"></ql-r-team-name> : Fixtures 
       </v-toolbar-title>
    </v-toolbar>"""
  
  props("id")
  subscription("team","id")(c => TeamService.get(c.id))
}