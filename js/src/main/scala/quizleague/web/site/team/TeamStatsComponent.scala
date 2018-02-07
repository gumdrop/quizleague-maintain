package quizleague.web.site.team

import quizleague.web.core._
import scalajs.js
import com.felstar.scalajs.vue._
import quizleague.web.model._
import vuechart._

object TeamStatsPage extends RouteComponent{
  
  val template = """<ql-team-stats :id="$route.params.id"></ql-team-stats>"""
  components(TeamStatsComponent)

}


object TeamStatsComponent extends Component with GridSizeComponentConfig{
  val name = "ql-team-stats"
  val template = """
    <v-tabs>
      <v-tabs-bar>
        <v-tabs-item ripple key="1" href="#tab1" >
          Single Season
        </v-tabs-item>
        <v-tabs-item ripple key="2" href="#tab2" >
          All Seasons
        </v-tabs-item>
        <v-tabs-slider color="yellow"></v-tabs-slider>
      </v-tabs-bar>
      <v-tabs-items>
        <v-tabs-content key="1" id="tab1">
          <v-container v-bind="gridSize" fluid>
            <v-layout column>
            <v-flex><ql-season-select :season="season" label="Season"></ql-season-select></v-flex>
            <v-flex><season-stats v-if="id && s" :teamId="id" :seasonId="s.id"></season-stats><v-flex>
            </v-layout>
          </v-container>
        </v-tabs-content>
        <v-tabs-content key="2" id="tab2">
          <v-container v-bind="gridSize" fluid>
            <v-layout column>
            <v-flex><all-season-stats v-if="id" :teamId="id"></all-season-stats><v-flex>
            </v-layout>
          </v-container>
        </v-tabs-content>
      </v-tabs-items>
    </v-tabs>


"""
  components(TeamStatsSeasonComponent,TeamStatsAllSeasonsComponent)
  
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
  <v-layout row wrap v-if="teamId && seasonId && stats" justify-space-around>     
    <v-flex >
      <season-league-position :stats="stats"></season-league-position>
    </v-flex>
    <v-flex >
      <season-match-scores :stats="stats"></season-match-scores>
    </v-flex>
    <v-flex >
      <season-cumu-diff :stats="stats"></season-cumu-diff>
    </v-flex>
    <v-flex >
      <season-cumu-scores :stats="stats"></season-cumu-scores>
    </v-flex>
  </v-layout>
"""
  components(SeasonLeaguePositionComponent, SeasonMatchScoresComponent, SeasonCumulativeScoresComponent,SeasonCumulativeDifferenceComponent)
  
  props("teamId","seasonId")
  
  subscription("stats","teamId","seasonId")( c => StatisticsService.teamStats(c.teamId, c.seasonId))
  
}

object TeamStatsAllSeasonsComponent extends Component{
  
  type facade = TeamStatsSeasonComponent
  
  val name = "all-season-stats"
  
  val template = """
  <v-layout row wrap v-if="teamId && stats" justify-space-around>     
    <v-flex >
      <seasons-league-position :stats="stats"></seasons-league-position>
    </v-flex>
    <v-flex >
      <seasons-mean-scores :stats="stats"></seasons-mean-scores>
    </v-flex>
  </v-layout>
"""
  components(AllSeasonsAverageScoreComponent,AllSeasonsLeaguePositionComponent)
  
  props("teamId")
  
  subscription("stats","teamId")( c => StatisticsService.allTeamStats(c.teamId))
  
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
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart width="400px" height="300px" v-if="stats && teamCount" type="line" :data="data()" :options="{maintainAspectRatio:false,responsive:false,legend:{display:false},scales:{yAxes:[{type:'linear', ticks:{reverse:true,min:1,max:teamCount,stepSize:1}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
  
  components(ChartComponent)
  
  prop("stats")
  method("data")({(c:facade) => StatisticsService.positionData(c.stats)}:js.ThisFunction)
  subscription("teamCount","stats")(c => StatisticsService.teamsInTable(c.stats))
  watch("stats")((c:facade,x:js.Any) => c.$forceUpdate())
}

object SeasonMatchScoresComponent extends Component{
  
  type facade = SeasonGraphComponent
  
  val name = "season-match-scores"
  val template = """
        <v-card>
        <v-card-title>Match Points</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart width="400px" height="300px" v-if="stats" type="line" :data="data()" :options="{maintainAspectRatio:false,responsive:false,spanGaps:true,scales:{yAxes:[{type:'linear', ticks:{stepSize:10}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
  
    components(ChartComponent)
  
  prop("stats")
  method("data")({(c:facade) => StatisticsService.matchScoresData(c.stats)}:js.ThisFunction)
  //data("data")(c => StatisticsService.matchScoresData(c.stats))
  watch("stats")((c:facade, x:js.Any) => c.$forceUpdate())
}

object SeasonCumulativeDifferenceComponent extends Component{
  
  type facade = SeasonGraphComponent
  
  val name = "season-cumu-diff"
  val template = """
        <v-card>
        <v-card-title>Cumulative Points Difference</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart width="400px" height="300px" v-if="stats" type="line" :data="data()" :options="{maintainAspectRatio:false,responsive:false,legend:{display:false},spanGaps:true,scales:{yAxes:[{type:'linear', ticks:{stepSize:50}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
    components(ChartComponent)
    
  prop("stats")
  method("data")({(c:facade) => StatisticsService.cumuDiffData(c.stats)}:js.ThisFunction)
  watch("stats")((c:facade, x:js.Any) => c.$forceUpdate())
}

object SeasonCumulativeScoresComponent extends Component{
  
  type facade = SeasonGraphComponent
  
  val name = "season-cumu-scores"
  val template = """
        <v-card>
        <v-card-title>Cumulative Scores</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart width="400px" height="300px" v-if="stats" type="line" :data="data()" :options="{maintainAspectRatio:false,responsive:false,spanGaps:true,scales:{yAxes:[{type:'linear', ticks:{stepSize:200}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
    components(ChartComponent)
    
  prop("stats")
  method("data")({(c:facade) => StatisticsService.cumuScoresData(c.stats)}:js.ThisFunction)
  watch("stats")((c:facade, x:js.Any) => c.$forceUpdate())
}


@js.native
trait AllSeasonsGraphComponent extends VueRxComponent{
  val stats:js.Array[Statistics]
}
object AllSeasonsAverageScoreComponent extends Component{
  
  type facade = AllSeasonsGraphComponent
  
  val name = "seasons-mean-scores"
  val template = """
        <v-card>
        <v-card-title>Average Scores</v-card-title>
          <v-card-text v-if="data" >
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart width="400px" height="300px" type="line" :data="data" :options="{maintainAspectRatio:false,responsive:false,scales:{yAxes:[{type:'linear', ticks:{stepSize:10}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
    components(ChartComponent)
    
  prop("stats")
  subscription("data","stats")(c => StatisticsService.allSeasonsAverageData(c.stats))

}

object AllSeasonsLeaguePositionComponent extends Component{
  
  type facade = AllSeasonsGraphComponent
  
  val name = "seasons-league-position"
  val template = """
        <v-card>  
          <v-card-title>League Position</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart width="400px" height="300px" v-if="stats && teamCount" type="line" :data="data" :options="{maintainAspectRatio:false,responsive:false,legend:{display:false},scales:{yAxes:[{type:'linear', ticks:{reverse:true,min:1,max:teamCount,stepSize:1}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
  
  components(ChartComponent)
  
  prop("stats")
  subscription("data")(c => StatisticsService.allSeasonsPositionData(c.stats))
  subscription("teamCount")(c => StatisticsService.teamsInTables(c.stats))
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