package quizleague.web.maintain.competition

import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._

import scala.scalajs.js
import TemplateElements._
import quizleague.web.maintain.text.TextService
import quizleague.web.util.Logging
import quizleague.web.maintain.venue.VenueService
import quizleague.web.util.component.SelectUtils


object SingletonCompetitionComponent extends CompetitionComponentConfig{
  val template = s"""
  <v-container v-if="item && season">
    <h2>Singleton Competition Detail {{item.startYear}}/{{item.endYear}}</h2>
    <v-form v-model="valid">
      <v-layout column>
        <v-text-field label="Name" required v-model="item.name" :rules=${valRequired("Name")}></v-text-field>
        <v-text-field label="Text Name" required v-model="item.textName" :rules=${valRequired("Text Name")}></v-text-field>
        <v-text-field label="Icon Name" v-model="item.icon" :append-icon="item.icon" ></v-text-field>
         <v-layout column v-if="item.event">
          <v-layout row>
            <v-text-field label="Date" required v-model="item.event.date" type="date" :rules=${valRequired("Date")}></v-text-field>
            <v-text-field label="Time" required v-model="item.event.time" type="time" :rules=${valRequired("Time")}></v-text-field>
            <v-text-field label="Duration" required v-model.number="item.event.duration" :rules=${valRequired("Duration")} type="number" step="0.5"></v-text-field>
          </v-layout>
          <v-select label="Venue" v-model="item.event.venue" :items="venues"></v-select>
         </v-layout>

      <div><v-btn v-on:click="editText(item.text.id)" text  ><v-icon>mdi-card-text-outline</v-icon>Text...</v-btn></div>
      </v-layout>
      $formButtons
    </form>
  </div>

  """  
      
  def venues() = SelectUtils.model[Venue](VenueService)(_.name)
  
  subscription("venues"){c:facade => venues()}

  
}