package quizleague.web.site.competition.statistics

import quizleague.web.core._
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig
import quizleague.web.site.SideMenu
import quizleague.web.site.team.TeamNameComponent



object CompetitionStatisticsPage extends RouteComponent{
  val template = """<competition :id="$route.params.id"></competition>"""
  components(CompetitionStatisticsComponent)
}

object CompetitionStatisticsComponent extends Component with GridSizeComponentConfig{
  type facade = IdComponent
  val name = "competition"
  val template = """
  <v-container v-bind="gridSize" fluid v-if="item">
    <v-layout column v-bind="gridSize">
      <table>
      <tr v-for="result in item.results" :key="result.competitionName">
        <td>{{result.seasonText}}</td><td>{{result.teamText}}<ql-team-name v-if="result.team" :team="result.team"></ql-team-name></td>
      </tr>

      </table>

      </v-flex>
      </v-layout>
   </v-container>
"""
  
  props("id")
 
  subscription ("item","id")(c => CompetitionStatisticsService.get(c.id))
      
  components (TeamNameComponent)
}
