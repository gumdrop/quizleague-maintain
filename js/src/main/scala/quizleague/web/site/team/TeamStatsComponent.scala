package quizleague.web.site.team

import quizleague.web.core._

import scalajs.js
import js.Dynamic.literal
import js.DynamicImplicits._
import js.JSConverters._
import com.felstar.scalajs.vue._
import quizleague.web.model._
import quizleague.web.util.component.{SelectUtils, SelectWrapper}
import rxscalajs.Observable
import vuechart._
import quizleague.web.util.Logging._
import quizleague.web.util.rx.RefObservable

object TeamStatsPage extends RouteComponent{
  
  val template = """<ql-team-stats :id="$route.params.id"></ql-team-stats>"""
  components(TeamStatsComponent)

}


object TeamStatsComponent extends Component with GridSizeComponentConfig{
  val name = "ql-team-stats"
  val template = """
    <v-tabs ripple slider-color="yellow" >

        <v-tab key="1">
          Single Season
        </v-tab>
        <v-tab key="2" >
          All Seasons
        </v-tab>
        <v-tab key="3" >
          Head-to-head
        </v-tab>
        <v-tab-item key="1">
          <v-container v-bind="gridSize" fluid>
            <v-layout column>
            <v-flex><ql-season-select :season="season" label="Season" :inline="false"></ql-season-select></v-flex>
            <v-flex><season-stats v-if="id && s" :teamId="id" :seasonId="s.id"></season-stats><v-flex>
            </v-layout>
          </v-container>
        </v-tab-item>
        <v-tab-item key="2" >
          <v-container v-bind="gridSize" fluid>
            <v-layout column>
            <v-flex><all-season-stats v-if="id" :teamId="id"></all-season-stats><v-flex>
            </v-layout>
          </v-container>
        </v-tab-item>
        <v-tab-item key="2" >
          <v-container v-bind="gridSize" fluid>
            <v-layout column>
            <v-flex><stats-head-to-head v-if="id" :teamId="id"></stats-head-to-head><v-flex>
            </v-layout>
          </v-container>
        </v-tab-item>

    </v-tabs>


"""
  components(TeamStatsSeasonComponent,TeamStatsAllSeasonsComponent, HeadToHeadComponent)
  
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
    <v-flex >
      <season-result-types :stats="stats"></season-result-types>
    </v-flex>
  </v-layout>
"""
  components(SeasonLeaguePositionComponent, SeasonMatchScoresComponent, SeasonCumulativeScoresComponent,SeasonCumulativeDifferenceComponent, SeasonResultTypeComponent)
  
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
    <v-flex >
      <seasons-result-types :stats="stats"></seasons-result-types>
    </v-flex>
  </v-layout>
"""
  components(AllSeasonsAverageScoreComponent,AllSeasonsLeaguePositionComponent,AllSeasonsResultTypeComponent)
  
  props("teamId")
  
  subscription("stats","teamId")( c => StatisticsService.allTeamStats(c.teamId))
  
}

trait GraphSizeComponentConfig extends Component{
  import js.DynamicImplicits._
  
  type facade <: VueRxComponent with VuetifyComponent
  
  def width(c:facade) = if(c.$vuetify.breakpoint.xsOnly) "300px" else "400px"
  computed("width")({width _}:js.ThisFunction)

}


@js.native
trait SeasonGraphComponent extends VueRxComponent with VuetifyComponent{
  val stats:Statistics
}

object SeasonLeaguePositionComponent extends Component with GraphSizeComponentConfig{
  
  type facade = SeasonGraphComponent
  
  val name = "season-league-position"
  val template = """
        <v-card>  
          <v-card-title>League Position</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" v-if="stats && teamCount" type="line" :data="data()" :options="{maintainAspectRatio:false,responsive:false,legend:{display:false},scales:{yAxes:[{type:'linear', ticks:{reverse:true,min:1,max:teamCount,stepSize:1}}]}}"></chart>
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

object SeasonMatchScoresComponent extends Component with GraphSizeComponentConfig{
  
  type facade = SeasonGraphComponent 
  
  val name = "season-match-scores"
  val template = """
        <v-card>
        <v-card-title>Match Points</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" v-if="stats" type="line" :data="data()" :options="{maintainAspectRatio:false,responsive:false,spanGaps:true,scales:{yAxes:[{type:'linear', ticks:{stepSize:10}}]}}"></chart>
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

object SeasonCumulativeDifferenceComponent extends Component with GraphSizeComponentConfig{
  
  type facade = SeasonGraphComponent
  
  val name = "season-cumu-diff"
  val template = """
        <v-card>
        <v-card-title>Cumulative Points Difference</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" v-if="stats" type="line" :data="data()" :options="{maintainAspectRatio:false,responsive:false,legend:{display:false},spanGaps:true,scales:{yAxes:[{type:'linear', ticks:{stepSize:50}}]}}"></chart>
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

object SeasonCumulativeScoresComponent extends Component with GraphSizeComponentConfig{
  
  type facade = SeasonGraphComponent
  
  val name = "season-cumu-scores"
  val template = """
        <v-card>
        <v-card-title>Cumulative Scores</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" v-if="stats" type="line" :data="data()" :options="{maintainAspectRatio:false,responsive:false,spanGaps:true,scales:{yAxes:[{type:'linear', ticks:{stepSize:200}}]}}"></chart>
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

object SeasonResultTypeComponent extends Component with GraphSizeComponentConfig{

type facade = SeasonGraphComponent

val name = "season-result-types"
val template = """
        <v-card>
        <v-card-title>Results</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" v-if="stats" type="pie" :data="data()" :options="{maintainAspectRatio:true,responsive:false}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
components(ChartComponent)

prop("stats")
method("data")({(c:facade) => StatisticsService.resultTypeData(js.Array(c.stats))}:js.ThisFunction)
watch("stats")((c:facade, x:js.Any) => c.$forceUpdate())
}


@js.native
trait AllSeasonsGraphComponent extends VueRxComponent with VuetifyComponent{
  val stats:js.Array[Statistics]
}
object AllSeasonsAverageScoreComponent extends Component with GraphSizeComponentConfig{
  
  type facade = AllSeasonsGraphComponent
  
  val name = "seasons-mean-scores"
  val template = """
        <v-card>
        <v-card-title>Average Scores</v-card-title>
          <v-card-text v-if="data" >
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" type="line" :data="data" :options="{maintainAspectRatio:false,responsive:false,scales:{yAxes:[{type:'linear', ticks:{stepSize:10}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
    components(ChartComponent)
    
  prop("stats")
  subscription("data","stats")(c => StatisticsService.allSeasonsAverageData(c.stats))

}

object AllSeasonsResultTypeComponent extends Component with GraphSizeComponentConfig{

  type facade = AllSeasonsGraphComponent

  val name = "seasons-result-types"
  val template = """
        <v-card>
        <v-card-title>Results</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" v-if="stats" type="pie" :data="data()" :options="{maintainAspectRatio:true,responsive:false}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
  components(ChartComponent)

  prop("stats")
  method("data")({(c:facade) => StatisticsService.resultTypeData(c.stats)}:js.ThisFunction)
  watch("stats")((c:facade, x:js.Any) => c.$forceUpdate())
}

object AllSeasonsLeaguePositionComponent extends Component with GraphSizeComponentConfig{
  
  type facade = AllSeasonsGraphComponent
  
  val name = "seasons-league-position"
  val template = """
        <v-card>  
          <v-card-title>League Position</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" v-if="stats && teamCount" type="line" :data="data" :options="{maintainAspectRatio:false,responsive:false,legend:{display:false},scales:{yAxes:[{type:'linear', ticks:{reverse:true,min:1,max:teamCount,stepSize:1}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""
  
  components(ChartComponent)
  
  prop("stats")
  subscription("data")(c => StatisticsService.allSeasonsPositionData(c.stats))
  subscription("teamCount")(c => StatisticsService.teamsInTables(c.stats))
  watch("stats")((c:facade, x:js.Any) => c.$forceUpdate())
}

@js.native
trait HeadToHeadComponent extends VueRxComponent{
  val teamId:String
  val currentSeason:js.Array[Statistics]
  val chips:js.Array[SelectWrapper[Team]]
  
}

object HeadToHeadComponent extends Component{
  type facade = HeadToHeadComponent
  val name = "stats-head-to-head"
  val template = """
  <v-layout column >
  <v-flex v-if="teams">
   <v-combobox
     v-model="chips"
     :items="teams"
     label="Teams"
     chips
     clearable
     solo
     multiple
   >
     <template slot="selection" slot-scope="data">
       <v-chip
         :selected="data.selected"
         close
         @input="remove(data.item)"
       >
         {{ data.item.text }}
       </v-chip>
     </template>
   </v-combobox>
   </v-flex>
    <v-layout row wrap v-if="teamId && stats && allSeasons" justify-space-around>
      <v-flex >
        <head-to-head-league-position :stats="allSeasons"></head-to-head-league-position>
      </v-flex>
      <v-flex>
      <head-to-head-average-score :stats="allSeasons"></head-to-head-average-score>
      </v-flex>
      <v-flex>
        <head-to-head-results :stats="allSeasons[0]" :teams="chips"></head-to-head-results>
      </v-flex>

    </v-layout>
    </v-layout>
"""
  components(AllSeasonsAverageScoreComponent,HeadToHeadLeaguePositionComponent, HeadToHeadResultsComponent, HeadToHeadAverageScoreComponent)

  private def allSeasons(c:facade) = Observable
    .combineLatest(
      js.Array(c.teamId).concat
        (c.chips
        .map(_.value.id))
        .map(StatisticsService.allTeamStats _)
        .toSeq)
    .map(_.toJSArray)

  private def remove(c:facade, item:SelectWrapper[Team]): Unit ={
    c.chips -= item
  }
  
  props("teamId")
  data("chips",js.Array())

  subscription("stats","teamId")( c => StatisticsService.allTeamStats(c.teamId))
  subscription("teams")(c => SelectUtils.model[Team](TeamService)(_.name).map(_.filter(_.value.id != c.teamId)))
  subscription("allSeasons", "chips")(allSeasons _)

  method("remove")({remove _}:js.ThisFunction)
}



@js.native
trait HeadToHeadGraphComponent extends VueRxComponent with VuetifyComponent{
  val stats:js.Array[js.Array[Statistics]]
}

object HeadToHeadLeaguePositionComponent extends Component with GraphSizeComponentConfig{

  type facade = HeadToHeadGraphComponent

  val name = "head-to-head-league-position"
  val template = """
        <v-card>
          <v-card-title>League Position</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" v-if="stats && teamCount" type="line" :data="data" :options="{maintainAspectRatio:false,responsive:false,spanGaps:true,legend:{display:true,position:'right'},scales:{yAxes:[{type:'linear', ticks:{reverse:true,min:1,max:teamCount,stepSize:1}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""

  components(ChartComponent)

  prop("stats")
  subscription("data","stats")(c => StatisticsService.multipleTeamsAllSeasonsPositionData(c.stats))
  subscription("teamCount")(c => StatisticsService.teamsInTables(c.stats(0)))
}

object HeadToHeadAverageScoreComponent extends Component with GraphSizeComponentConfig{

  type facade = HeadToHeadGraphComponent

  val name = "head-to-head-average-score"
  val template = """
        <v-card>
          <v-card-title>Average Score</v-card-title>
          <v-card-text>
          <v-container fluid grid-list-sm>
            <v-layout row justify-space-around>
              <chart :width="width" height="300px" v-if="stats && teamCount" type="line" :data="data" :options="{maintainAspectRatio:false,responsive:false,spanGaps:true,legend:{display:true,position:'right'},scales:{yAxes:[{type:'linear', ticks:{stepSize:2}}]}}"></chart>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""

  components(ChartComponent)

  prop("stats")
  subscription("data","stats")(c => StatisticsService.multipleTeamsAllSeasonsAverageData(c.stats))
  subscription("teamCount")(c => StatisticsService.teamsInTables(c.stats(0)))
}




@js.native
trait HeadToHeadResultsComponent extends VueRxComponent with VuetifyComponent{
  val stats:js.Array[Statistics]
  val teams:js.Array[SelectWrapper[Team]]
  var rows:js.Array[_]
}

object HeadToHeadResultsComponent extends Component with GraphSizeComponentConfig{

  type facade = HeadToHeadResultsComponent

  val name = "head-to-head-results"
  val template = """
        <v-card>
          <v-card-title>Results</v-card-title>
          <v-card-text v-if="rows">
           <v-container fluid grid-list-sm >
            <v-layout row justify-space-around>
              <v-data-table :headers="headers"
              :items="rows">
               <template slot="items" slot-scope="props">
               <td>{{props.item.team}}</td>
       <td >{{ props.item.win }}</td>
       <td >{{ props.item.lose }}</td>
       <td >{{ props.item.draw }}</td>

     </template>
              </v-data-table>
            </v-layout>
          </v-container>
          </v-card-text>
        </v-card>
"""

  components(TeamNameComponent)

  private def filter(c:facade):js.Array[HeadToHead] = c.stats
    .flatMap(_.seasonStats.headToHead)
    .groupBy(_.team.id).filter(h => c.teams.exists(_.value.id == h._1))
    .values.map(_.fold(new HeadToHead(null,0,0,0))((h1:HeadToHead,h2:HeadToHead) => h1 plus h2)).toJSArray

  private def items(items:js.Array[HeadToHead]) = Observable.combineLatest(items.map(x => x.team.obs.map(t => literal(team=t.shortName, win=x.win,lose=x.lose,draw=x.draw))).toSeq).map(_.toJSArray)


  prop("stats")
  prop("teams")
  data("rows", js.Array())
  data("headers", js.Array(literal(text="Team", value="team", sortable=false),literal(text="Won", value="win"), literal(text="Lost", value="lose"),literal(text="Drawn", value="draw")))
  subscription("rows", "teams")(c => {items(filter(c)).map(y => {c.rows = y;y})})



}


object TeamStatsTitle extends RouteComponent{
  val template = """<stats-title :id="$route.params.id"></stats-title>"""
 components(TeamStatsTitleComponent)
}

object TeamStatsTitleComponent extends Component{
  type facade = IdComponent
  
  val name = "stats-title"
  val template = """<v-toolbar      
      color="amber lighten-3"
      dense
      class="subtitle-background"
      v-if="team">
     <ql-title>{{team.name}} : Graphs and Statistics</ql-title>
      <v-toolbar-title>
        <ql-r-team-name :team="team"></ql-r-team-name> : Graphs and Statistics 
       </v-toolbar-title>
    </v-toolbar>"""
  
  props("id")
  subscription("team","id")(c => TeamService.get(c.id))
}