package quizleague.web.site.competition.statistics

import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.core._
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig
import quizleague.web.site.SideMenu
import quizleague.web.site.team.TeamNameComponent
import quizleague.web.model._
import rxscalajs.Observable

import scalajs.js


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
    <v-flex>
      <v-card>
      <v-card-text>
      <table class="roll-of-honour">
      <thead>
        <tr><th>Season</th><th>Winner</th></tr>
      </thead>
      <tr v-for="result in sortResults(item.results)" :key="result.competitionName">
        <td><result-season :result="result"></result-season></td><td><result-team :result="result"></result-team></td>
      </tr>

      </table>
      </v-card-text>
      </v-card>
      </v-flex>
      </v-layout>
   </v-container>
"""
  
  props("id")
 
  subscription ("item","id")(c => CompetitionStatisticsService.get(c.id))

  method("sortResults"){r:js.Array[ResultEntry] => r.sortBy(_.seasonText)}
      
  components (ResultSeasonComponent,ResultTeamComponent)
}



@js.native
trait ResultSeasonComponent extends VueRxComponent{
  var competition:Competition
  val result:ResultEntry
}

object ResultSeasonComponent extends Component{

  type facade = ResultSeasonComponent

  val name = "result-season"
  val template="""
  <span>
    <router-link :to="'/competition/' + competition.id + '/' + competition.typeName" v-if="competition">
     {{result.seasonText}}
    </router-link>
    <span v-if="!competition">
    {{result.seasonText}}
    </span>
  """

  prop("result")
  //watch("result"){(c:facade,x:Any) => c.competition = null}
  data("competition",null)

  subscription("competition", "result")(c => if(c.result.competition != null) c.result.competition.obs else Observable.just(null))

}

object ResultTeamComponent extends Component{

  val name = "result-team"
  val template="""
  <span>
    <router-link :to="'/team/' + result.team.id" v-if="result.team">
     <ql-team-name :team="result.team"></ql-team-name>
    </router-link>
    <span v-if="!result.team">
    {{result.teamText}}
    </span>
  """
  components(TeamNameComponent)

  prop("result")

}
