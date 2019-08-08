package quizleague.web.maintain.globaltext

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.maintain.component.ItemListComponentConfig
import quizleague.web.model._
import quizleague.web.names._

object GlobalTextListComponent extends ItemListComponentConfig[GlobalText] with RouteComponent with GlobalTextNames{
  val template = s"""
  <v-container>
    <v-layout column>
      <div v-for="item in items">
        <v-btn :to="'globaltext/' + item.id" text left>{{item.name}}</v-btn>
      </div>
      $addFAB
    </v-layout>
  </v-container>
"""
val service = GlobalTextService
}