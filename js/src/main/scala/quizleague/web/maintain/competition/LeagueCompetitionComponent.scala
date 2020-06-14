package quizleague.web.maintain.competition

import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._

import scala.scalajs.js
import TemplateElements._
import quizleague.web.maintain.season.SeasonService
import rxscalajs.Observable._

import js.JSConverters._
import quizleague.web.core._
import rxscalajs.Observable
import quizleague.web.util.Logging._
import quizleague.web.maintain.season.SeasonService
import quizleague.web.util.rx.RefObservable
import quizleague.web.maintain.fixtures.FixturesService
import quizleague.web.maintain.fixtures.FixtureService
import quizleague.web.util.component.{SelectObjectWrapper, SelectUtils, SelectWrapper}



@js.native
trait LeagueCompetitionComponent extends CompetitionComponent{
  var subsidiaries:js.Array[SelectWrapper[Competition]]
  var subsidiary:LeagueCompetition
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
          <v-select label="Subsidiary" :items="subsidiaries" v-model="subsidiary"></v-select>
          <div><v-btn text v-if="subsidiary" type="button" v-on:click="copyFixturesToSubsidiary(item)"><v-icon>mdi-file-copy</v-icon>Copy fixtures to subsidiary</v-btn></div>

      <div><v-btn text v-on:click="editText(item.text.id)"  type="button" ><v-icon>mdi-card-text-outline</v-icon>Text...</v-btn></div>
      <div><v-btn text v-on:click="fixtures(item)" ><v-icon>mdi-check</v-icon>Fixtures...</v-btn></div>
      <div>
       <span>Tables</span>&nbsp;<v-btn v-on:click="addTable()" icon><v-icon>mdi-plus</v-icon></v-btn>  <v-chip close v-on:click="toTable(table.id)" @input="removeTable(table.id)" v-for="(table,index) in async(item.leaguetable)" :key="table.id">{{table.description || 'Table ' + (index + 1)}}</v-chip>
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
  
  def subsidiaries(seasonId:String):Observable[js.Array[SelectObjectWrapper[Competition]]] = {
    SeasonService.get(seasonId).flatMap(season => SelectUtils.objectModel(season.competition)(_.name)(filterSubs _))
  }
  
  def copyFixturesToSubsidiary(c:facade, item:LeagueCompetition) = {
    log(c.subsidiary.id,"subsidiary")
    c.showProgress = true
    FixturesService.copyFixtures(item.fixtures, c.subsidiary).subscribe(x => c.showProgress = false)

  }
  
  
  data("showProgress", false)
  data("subsidiary", null)
  subscription("subsidiaries")(c => subsidiaries(c.$route.params("seasonId")))
  method("copyFixturesToSubsidiary")({copyFixturesToSubsidiary _}:js.ThisFunction)
  
 

}
    