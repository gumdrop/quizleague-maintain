package quizleague.web.maintain.team

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.maintain.component.ItemListComponentConfig
import quizleague.web.model._
import quizleague.web.names._
import scalajs.js

object TeamListComponent extends ItemListComponentConfig[Team] with RouteComponent with TeamNames{
  
  override def sort(items:js.Array[Team]) = items.sortBy(_.shortName)
  
  val template = s"""
  <v-container>
    <v-layout column>
      <div v-for="item in items" :key="item.id">
        <v-btn :to="'team/' + item.id" text left>{{item.name}}</v-btn>
      </div>
      $addFAB
    </v-layout>
  </v-container>
"""
val service = TeamService
}