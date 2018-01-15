package quizleague.web.maintain.season

import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.maintain.component.ItemComponentConfig._
import quizleague.web.core.RouteComponent
import quizleague.web.model._
import quizleague.web.maintain.component.TemplateElements._
import scalajs.js
import js.JSConverters._
import quizleague.web.maintain.competition.CompetitionService

@js.native
trait SeasonComponent extends ItemComponent[Season]{
  var selectedType:String
}

object SeasonComponent extends ItemComponentConfig[Season] with RouteComponent {

  override type facade = SeasonComponent
    


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

        <div><v-btn v-on:click ="editText(item.text.id)" flat><v-icon>description</v-icon>Text</v-btn></div>
        <div><v-btn v-on:click ="calendar(item.text.id)" flat><v-icon>mdi-calendar</v-icon>Calendar</v-btn></div>
        <v-layout column>
          <v-select @input="addCompetition(selectedType)" clearable append-icon="add" v-model="selectedType" label="Add Competition" :items="types"></v-select>
        <div>
          <v-chip close v-on:click="editCompetition(async(c))" @input="removeCompetition(c.id)" v-for="c in item.competitions" :key="c.id">{{async(c).name}}</v-chip>
        </div>
        </v-layout>
        $chbxRetired 
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""
    
     
       val service = SeasonService
  val competitionService = CompetitionService
  
  def removeCompetition(c:facade, id:String) = c.item.competitions ---= id
  
  def addCompetition(c:facade,typeName:String) = {
      val comp:Competition = competitionService.instance(CompetitionType.withName(typeName))
      c.item.competitions +++= (comp.id,comp)
      c.selectedType = null
      editCompetition(c,comp)
    }
  
  def editCompetition(c:facade, comp: Competition) = {
    service.cache(c.item)
    c.$router.push(s"${c.item.id}/competition/${comp.id}/${comp.typeName}")
  }
  def calendar(c:facade) = {
      service.cache(c.item)
      c.$router.push(s"${c.item.id}/calendar")
    }
     
  method("removeCompetition")({removeCompetition _}:js.ThisFunction)
  method("addCompetition")({addCompetition _}:js.ThisFunction)
  method("calendar")({calendar _}:js.ThisFunction)
  method("editCompetition")({editCompetition _}:js.ThisFunction)

  data("types",CompetitionType.values.map(_.toString()).toJSArray)
  data("selectedType",null)
  data("valid",true)

}

