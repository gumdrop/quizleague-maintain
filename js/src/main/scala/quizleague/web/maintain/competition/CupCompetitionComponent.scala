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
import quizleague.web.util.component.SelectUtils


object CupCompetitionComponent extends CompetitionComponentConfig{

  val   template = s"""
  <v-container>
    <h2>Cup Competition Detail {{season.startYear}}/{{season.endYear}}</h2>

    <v-form v-model="valid"  v-if="item && season">
      <v-layout column>
   
          <v-text-field  label="Name" type="text" v-model="item.name"
             required :rules=${valRequired("Name")}></v-text-field>
          <v-text-field  label="Start Time" type="time" v-model="item.startTime"
             required :rules=${valRequired("Start Time")}></v-text-field>
          <v-text-field  label="Duration" type="number" v-model.number="item.duration"
             required step="0.5" :rules=${valRequired("Duration")}></v-text-field>
          <v-text-field label="Text Name" required v-model="item.textName" :rules=${valRequired("Text Name")}></v-text-field>
          <v-text-field label="Icon Name" v-model="item.icon" :append-icon="item.icon" ></v-text-field>
      <div><v-btn text v-on:click="editText(item.text.id)"  type="button" ><v-icon>mdi-card-text-outline</v-icon>Text...</v-btn></div>
      <div><v-btn text v-on:click="fixtures(item)" ><v-icon>mdi-check</v-icon>Fixtures...</v-btn></div>
      </v-layout>
      $formButtons
    </v-form>
  </v-container>"""
 
}
    