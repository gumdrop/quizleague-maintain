package quizleague.web.maintain.season

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.maintain.component.ItemListComponentConfig
import quizleague.web.model._
import quizleague.web.names._
import scalajs.js
import scalajs.js.JSConverters._

object SeasonListComponent extends ItemListComponentConfig[Season] with RouteComponent with SeasonNames{
  
  override def sort(items:js.Array[Season]) = items.sortBy(_.startYear)
  
  val template = s"""
  <v-container v-if="items">
    <v-layout column>
      <div v-for="item in items">
        <v-btn :to="'season/' + item.id" text left>{{item.startYear}}/{{item.endYear}}</v-btn>
      </div>
      $addFAB
    </v-layout>
  </v-container>
"""
  val service = SeasonService

}