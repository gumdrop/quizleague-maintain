package quizleague.web.maintain.competitionstatistics

import quizleague.web.core._
import quizleague.web.maintain.component.ItemListComponentConfig
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.model._
import quizleague.web.names._

import scala.scalajs.js

object CompetitionStatisticsListComponent extends ItemListComponentConfig[CompetitionStatistics] with RouteComponent with CompetitionStatisticsNames{
  
  override def sort(items:js.Array[CompetitionStatistics]) = items.sortBy(_.competitionName)
  
  val template = s"""
  <v-container>
    <v-layout column>
      <div v-for="item in items" :key="item.id">
        <v-btn :to="'competitionstatistics/' + item.id" text left>{{item.competitionName}}</v-btn>
      </div>
      $addFAB
    </v-layout>
  </v-container>
"""
val service = CompetitionStatisticsService
}