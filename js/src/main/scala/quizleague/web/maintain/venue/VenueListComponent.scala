package quizleague.web.maintain.venue

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.maintain.component.ItemListComponentConfig
import quizleague.web.model._
import quizleague.web.names._
import scalajs.js

object VenueListComponent extends ItemListComponentConfig[Venue] with RouteComponent with VenueNames{
  
  override def sort(items:js.Array[Venue]) = items.sortBy(_.name)
  
  val template = s"""
  <v-container>
    <v-layout column>
      <div v-for="item in items">
        <v-btn :to="'venue/' + item.id" text left>{{item.name}}</v-btn>
      </div>
      $addFAB
    </v-layout>
  </v-container>
"""
val service = VenueService
}