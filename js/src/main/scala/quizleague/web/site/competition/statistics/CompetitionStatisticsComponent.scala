package quizleague.web.site.competition.statistics

import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.core._
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig
import quizleague.web.site.team.{ResponsiveTeamNameComponent, TeamNameComponent, TeamService}
import quizleague.web.model._
import quizleague.web.util.rx.RefObservable
import quizleague.web.site.competition.CompetitionService

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
        <th>Season</th><th>Winner</th>
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
    <competition-link :competition="result.competition" id="result.competition.id" v-if="result.competition">
     {{result.seasonText}}
    </competition-link>
    <span v-if="!result.competition">
    {{result.seasonText}}
    </span>
    </span>
  """
  components(CompetitionLinkComponent)

  prop("result")

}


@js.native
trait CompetitionLinkComponent extends VueRxComponent{
  val id:String
  val competition:RefObservable[Competition]
}

object CompetitionLinkComponent extends Component{

  type facade = CompetitionLinkComponent

val name = "competition-link"
val template="""
    <span>
      <v-skeleton-loader v-if="!comp" type="text" width="5em"></v-skeleton-loader>
      <router-link :to="'/competition/'+ comp.key.encode + '/' + comp.typeName" v-else>
       <slot></slot>
      </router-link>
    </span>
  """

  prop("competition")
  prop("id")
  subscription("comp")(c => c.competition.obs)

}

object ResultTeamComponent extends Component{

  val name = "result-team"
  val template="""
  <span>
    <team-link v-if="result.team" :id="result.team.id"></team-link>
    <span v-if="!result.team">
    {{result.teamText}}
    </span>
    </span>
  """
  components(TeamLinkComponent)

  prop("result")

}

object TeamLinkComponent extends Component{

  type facade = IdComponent

  val name = "team-link"
  val template = """
    <router-link :to="'/team/' + id">
     <ql-r-team-name :id="id"></ql-r-team-name>
    </router-link>"""

  components(ResponsiveTeamNameComponent)

  prop("id")
}
