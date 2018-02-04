package quizleague.web.site.team

import quizleague.web.core._
import scalajs.js
import com.felstar.scalajs.vue._
import quizleague.web.model._

object TeamStatsPage extends RouteComponent{
  
  val template = """<ql-team-stats :id="$route.params.id"></ql-team-stats>"""
  components(TeamStatsComponent)

}


object TeamStatsComponent extends Component with GridSizeComponentConfig{
  val name = "ql-team-stats"
  val template = """

    <v-container v-bind="gridSize" fluid>
      <v-layout column>
      <v-flex><ql-season-select :season="season" label="Season"></ql-season-select></v-flex>
      <v-flex><season-stats v-if="id && s" :teamId="id" :seasonId="s.id"></season-stats><v-flex>
      </v-layout>
    </v-container>

"""
  components(TeamStatsSeasonComponent)
  
  prop("id")
  data("season", TeamViewService.season)
  subscription("s")(c => TeamViewService.season)
}

@js.native
trait TeamStatsSeasonComponent extends VueRxComponent{
  val teamId:String
  val seasonId:String
}

object TeamStatsSeasonComponent extends Component{
  
  type facade = TeamStatsSeasonComponent
  
  val name = "season-stats"
  
  val template = """
  <v-layout row wrap v-if="teamId && seasonId && stats">     
    <v-flex >
      <season-league-position :stats="stats"></season-league-position>
    </v-flex>
    <v-flex >
      <season-match-scores :stats="stats"></season-match-scores>
    </v-flex>
    <v-flex >
      <season-cumu-scores :stats="stats"></season-cumu-scores>
    </v-flex>
  </v-layout>
"""
  components(SeasonLeaguePositionComponent, SeasonMatchScoresComponent, SeasonCumulativeScoresComponent)
  
  props("teamId","seasonId")
  
  subscription("stats","teamId","seasonId")( c => StatisticsService.teamStats(c.teamId, c.seasonId))
  
}

@js.native
trait SeasonGraphComponent extends VueRxComponent{
  val stats:Statistics
}

object SeasonLeaguePositionComponent extends Component{
  
  type facade = SeasonGraphComponent
  
  val name = "season-league-position"
  val template = """
        <v-card>  
          <v-card-title>League Position</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-xl>
            <v-layout row justify-space-around>
              <v-flex style="width:500px;height:500px"><vue-chart v-if="stats && teamCount" type="line" :data="data()" :options="{maintainAspectRatio:false,scales:{yAxes:[{type:'linear', ticks:{reverse:true,min:1,max:teamCount,stepSize:1}}]}}"></vue-chart></v-flex>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
    
  prop("stats")
  method("data")({(c:facade) => StatisticsService.positionData(c.stats)}:js.ThisFunction)
  subscription("teamCount")(c => StatisticsService.teamsInTable(c.stats))
}

object SeasonMatchScoresComponent extends Component{
  
  type facade = SeasonGraphComponent
  
  val name = "season-match-scores"
  val template = """
        <v-card>
        <v-card-title>Match Points</v-card-title>
          <v-card-text >
          <v-container fluid grid-list-xl>
            <v-layout row justify-space-around>
              <v-flex style="width:500px;height:500px"><vue-chart v-if="data" type="line" :data="data" :options="{maintainAspectRatio:false,scales:{yAxes:[{type:'linear', ticks:{stepSize:10}}]}}"></vue-chart></v-flex>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
    
  prop("stats")
  data("data")(c => StatisticsService.matchScoresData(c.stats))
}

object SeasonCumulativeScoresComponent extends Component{
  
  type facade = SeasonGraphComponent
  
  val name = "season-cumu-scores"
  val template = """
        <v-card>
        <v-card-title>Cumulative Points Difference</v-card-title>
          <v-card-text >
          <v-container fluid grid-list-xl>
            <v-layout row justify-space-around>
              <v-flex style="width:500px;height:500px"><vue-chart v-if="data" type="line" :data="data" :options="{maintainAspectRatio:false,scales:{yAxes:[{type:'linear', ticks:{stepSize:50}}]}}"></vue-chart></v-flex>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
    
  prop("stats")
  data("data")(c => StatisticsService.cumuScoresData(c.stats))
}

object TeamStatsTitle extends RouteComponent{
  val template = """<stats-title :id="$route.params.id"></stats-title>"""
 components(TeamStatsTitleComponent)
}

object TeamStatsTitleComponent extends Component{
  type facade = IdComponent
  
  val name = "stats-title"
  val template = """<v-toolbar      
      color="amber darken-3"
      dark
      clipped-left v-if="team">
     <ql-title>{{team.name}} : Graphs and Statistics</ql-title>
      <v-toolbar-title class="white--text">
        {{team.name}} : Graphs and Statistics 
       </v-toolbar-title>
    </v-toolbar>"""
  
  props("id")
  subscription("team","id")(c => TeamService.get(c.id))
}