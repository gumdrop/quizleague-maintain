package quizleague.web.maintain.user

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.maintain.component.ItemListComponentConfig
import quizleague.web.model._
import quizleague.web.names._
import scalajs.js

object UserListComponent extends ItemListComponentConfig[User] with RouteComponent with UserNames{
  
  override def sort(items:js.Array[User]) = items.sortBy(_.name)
  
  val template = s"""
  <v-container>
    <v-layout column>
      <div v-for="item in items">
        <v-btn :to="'user/' + item.id" text left>{{item.name}}</v-btn>
      </div>
      $addFAB
    </v-layout>
  </v-container>
"""
val service = UserService
}