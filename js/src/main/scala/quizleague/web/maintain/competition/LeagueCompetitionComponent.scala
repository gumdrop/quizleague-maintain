package quizleague.web.maintain.competition

import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.maintain.component._
import quizleague.web.maintain.fixtures.FixturesService
import quizleague.web.maintain.season.SeasonService
import quizleague.web.model._
import quizleague.web.util.component.SelectWrapper
import rxscalajs.Observable

import scala.scalajs.js



@js.native
trait LeagueCompetitionComponent extends CompetitionComponent{
  var subsidiaries:js.Array[SelectWrapper[Competition]]
  var showProgress:Boolean
}
object LeagueCompetitionComponent extends CompetitionComponentConfig{
  override type facade = LeagueCompetitionComponent
  val template = s"""
  <v-container v-if="item && season && subsidiaries">
    <h2>League Competition Detail {{season.startYear}}/{{season.endYear}}</h2>
    <div style="position:absolute;top:2em;right:2em;" v-if="showProgress"><v-progress-circular indeterminate="true"></v-progress-circular></div>
    <v-form v-model="valid"  >
      <v-layout column>
   
          <v-text-field  label="Name" type="text" v-model="item.name"
             required :rules=${valRequired("Name")}></v-text-field>
          <v-text-field  label="Start Time" type="time" v-model="item.startTime"
             required :rules=${valRequired("Start Time")}></v-text-field>
          <v-text-field  label="Duration" type="number" v-model.number="item.duration"
             required step="0.5" :rules=${valRequired("Duration")}></v-text-field>
          <v-text-field label="Text Name" required v-model="item.textName" :rules=${valRequired("Text Name")}></v-text-field>
          <v-text-field label="Icon Name" v-model="item.icon" :append-icon="item.icon" ></v-text-field>
          <div v-for="subsidiary in subsidiaries" :key="subsidiary.id"><v-btn text v-if="subsidiary" type="button" v-on:click="copyFixturesToSubsidiary(item, subsidiary)"><v-icon>mdi-file-copy</v-icon>Copy fixtures to {{subsidiary.name}}</v-btn></div>

      <div><v-btn text v-on:click="editText(item.text.id)"  type="button" ><v-icon>mdi-card-text-outline</v-icon>Text...</v-btn></div>
      <div><v-btn text v-on:click="fixtures(item)" ><v-icon>mdi-check</v-icon>Fixtures...</v-btn></div>
      <div v-if="tables">
       <span>Tables</span>&nbsp;<v-btn v-on:click="addTable()" icon><v-icon>mdi-plus</v-icon></v-btn>  <v-chip close v-on:click="toTable(table.id)" @click:close="removeTable(table)" v-for="(table,index) in tables" :key="table.id">{{table.description || 'Table ' + (1 + index)}}</v-chip>
      </div>
      </v-layout>
      $formButtons
    </v-form>
  </v-container>"""
  
  def filterSubs(c:Competition) = {
    c match {
      case s:SubsidiaryLeagueCompetition => true
      case _ => false
    }
  }
  
  def subsidiaries(seasonId:String):Observable[js.Array[Competition]] = {
    SeasonService.get(seasonId).flatMap(_.competition.map(_.filter(filterSubs _)))
  }
  
  def copyFixturesToSubsidiary(c:facade, item:LeagueCompetition, subsidiary:LeagueCompetition) = {
    c.showProgress = true
    FixturesService.copyFixtures(item.fixtures, subsidiary).subscribe(x => c.showProgress = false)

  }
  
  
  data("showProgress", false)
  data("subsidiary", null)
  subscription("subsidiaries")(c => subsidiaries(c.$route.params("seasonId")))
  method("copyFixturesToSubsidiary")({copyFixturesToSubsidiary _}:js.ThisFunction)
  
 

}
    