package quizleague.web.maintain.season

import java.util.logging.Logger

import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.maintain.component.ItemComponentConfig._
import quizleague.web.core.RouteComponent
import quizleague.web.model._
import quizleague.web.maintain.component.TemplateElements._

import scalajs.js
import js.JSConverters._
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.util.Logging

@js.native
trait SeasonComponent extends ItemComponent[Season]{
  var selectedType:String
}

object SeasonComponent extends ItemComponentConfig[Season] with RouteComponent {

  override type facade = SeasonComponent
  def parentKey(c:facade) = null


  val template = s"""
  <v-container v-if="item">
    <v-form v-model="valid" ref="fm">
      <v-layout column>
        <v-text-field
          label="Start Year"
          v-model="item.startYear"
          :rules=${valRequired("Start Year")}
          required
        ></v-text-field>
        <v-text-field
          label="End Year"
          v-model="item.endYear"
          :rules=${valRequired("End Year")}
          required
        ></v-text-field>

        <div><v-btn v-on:click ="editText(item.text.id)" text><v-icon>mdi-card-text-outline</v-icon>Text</v-btn></div>
        <div><v-btn v-on:click ="calendar(item.text.id)" text><v-icon>mdi-calendar</v-icon>Calendar</v-btn></div>
        <v-layout column>
          <v-select @click:append="addCompetition(item, selectedType)" clearable append-icon="mdi-plus" v-model="selectedType" label="Add Competition" :items="types"></v-select>
        <div>
          <v-chip close v-on:click="editCompetition(c)" @click:close="removeCompetition(c)" v-for="c in competitions" :key="c.id">{{c.name}}</v-chip>
        </div>
        </v-layout>
        $chbxRetired 
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""
    
     
  val service = SeasonService
  val competitionService = CompetitionService
  
  def removeCompetition(c:facade, competition:Competition) = {
    if(org.scalajs.dom.window.confirm("Delete ?")) {
      competitionService.delete(competition)
    }
  }
  
  def addCompetition(c:facade, item:Season, typeName:String) = {
      val compType = CompetitionType.withName(typeName)
      val comp:Competition = competitionService.instance(compType, item.key)
      competitionService.save(comp).subscribe(x => editCompetition(c,comp))
      c.selectedType = null

    }
  
  def editCompetition(c:facade, comp: Competition) = {
    c.$router.push(s"${c.item.id}/competition/${comp.id}/${comp.typeName}")
  }
  def calendar(c:facade) = {
      service.cache(c.item)
      c.$router.push(s"${c.item.id}/calendar")
    }

  subscription("competitions")((c:facade) => item(c).flatMap(_.competition))
  method("removeCompetition")({removeCompetition _}:js.ThisFunction)
  method("addCompetition")({addCompetition _}:js.ThisFunction)
  method("calendar")({calendar _}:js.ThisFunction)
  method("editCompetition")({editCompetition _}:js.ThisFunction)

  data("types",CompetitionType.values.map(_.toString()).toJSArray)
  data("selectedType",null)
  data("valid",true)

}

