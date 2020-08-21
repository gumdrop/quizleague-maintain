package quizleague.web.maintain.competitionstatistics

import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.core._
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.competitionstatistics.CompetitionStatisticsComponent.{facade, subscription}
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.maintain.season.SeasonComponent.facade
import quizleague.web.maintain.season.SeasonService
import quizleague.web.maintain.team.TeamService
import quizleague.web.maintain.user.UserService
import quizleague.web.maintain.venue._
import quizleague.web.model._
import quizleague.web.util.component.{SelectUtils, SelectWrapper}
import quizleague.web.util.Logging._
import quizleague.web.util.rx.RefObservable
import rxscalajs.Observable

import scala.scalajs.js
import js.JSConverters._

object CompetitionStatisticsComponent extends ItemComponentConfig[CompetitionStatistics] with RouteComponent {

  val service = CompetitionStatisticsService
  def parentKey(c:facade) = null

  val template = s"""
  <v-container v-if="item">
    <v-form v-model="valid" ref="fm">
      <v-layout column>
        <v-text-field
          label="Competition Name"
          v-model="item.competitionName"
          :rules=${valRequired("Competition Name")}
          required
        ></v-text-field>
        <h4>Results</h4>
        <div><v-btn v-on:click="addResult(item)" button><v-icon >mdi-plus</v-icon></v-btn></div>
        <div v-for="row in sortResults(item.results)" :key="row.id">
          <v-card>
            <v-card-text>
            <comp-stats-result :item="row" :competitionName="item.competitionName"></comp-stats-result>
            </v-card-text>
          </v-card>
          <br>
        </div>
        <div><v-btn v-on:click="addResult(item)" button><v-icon >mdi-plus</v-icon></v-btn></div>
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""
  components(CompetitionStatisticsResultComponent)

  method("addResult")((item:CompetitionStatistics) => item.results.push(service.resultEntryInstance()))
  method("sortResults")((results:js.Array[ResultEntry]) => results.sortBy(_.seasonText))
}

@js.native
trait CompetitionStatisticsResultComponent extends VueRxComponent{

  val item:ResultEntry
  val competitionName:String
  var competitions : js.Array[SelectWrapper[Competition]]
}


object CompetitionStatisticsResultComponent extends Component{

  override type facade = CompetitionStatisticsResultComponent

  val name = "comp-stats-result"
  
  val template =
    s"""
      <v-layout column>
        <v-layout row>
         <v-text-field
            label="Season Text"
            v-model="item.seasonText"
            :rules=${valRequired("Season Text")}
          ></v-text-field>
          <v-select
            label="Season"
            :items="seasons"
            v-model="item.season"

            >
          </v-select>
        </v-layout>
        <v-layout row>
         <v-text-field
            label="Team Text"
            v-model="item.teamText"
            :disabled="item.team"
          ></v-text-field>
          <v-select
            label="Team"
            :items="teams"
            v-model="item.team"
            >
          </v-select>
          <v-select v-if="competitions"
            label="Competition"
            :items="competitions"
            v-model="item.competition"
            >
          </v-select>
          <div v-if="!competitions && item.competition">
          Competition : {{async(item.competition).name}}
          </div>

        </v-layout>
      </v-layout>
    """

  
  
  val seasonService = SeasonService
  val teamService = TeamService
  val competitionService = CompetitionService
  def teams() = SelectUtils.model[Team](teamService)(_.name)
  def seasons() = SelectUtils.model[Season](seasonService)(_.toText)
  def handleSeasonChange(c:facade) = {
    if(c.item.season != null) {
      c.item.season.obs.subscribe({
        s => {
          c.item.seasonText = s.toText
          c.item.competition = null
          SelectUtils
            .model(s.competition.map(_.filter(_.name == c.competitionName)) , competitionService)(_.name).subscribe(cs => c.competitions = cs)
        }
      })}
  }

  prop("item")
  prop("competitionName")
  data("competitions",null)
  subscription("seasons"){c:facade => seasons()}
  subscription("teams"){c:facade => teams()}
  watch("item.season")((c:facade,x:Any) => handleSeasonChange(c))
  watch("item.team")((c:facade,x:Any) => if(c.item.team != null){c.item.teamText = null})

}