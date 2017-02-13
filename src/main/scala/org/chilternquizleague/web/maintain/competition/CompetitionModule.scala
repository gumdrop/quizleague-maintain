package org.chilternquizleague.web.maintain.competition

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{ Route, RouterModule }

import scala.scalajs.js

import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.web.maintain.component.ComponentNames

import angulate2.common.CommonModule
import org.chilternquizleague.web.maintain.results.ResultsModule
import org.chilternquizleague.web.maintain.fixtures.FixturesModule

@NgModule(
  imports = @@[CommonModule, FormsModule, MaterialModule, RouterModule, FlexLayoutModule, CompetitionRoutesModule,ResultsModule,FixturesModule],
  declarations = @@[CompetitionComponent,LeagueCompetitionComponent],
  providers = @@[CompetitionService])
class CompetitionModule

@Routes(
  root = false,
  Route(
    path = "competition/:id",
    component = %%[CompetitionComponent]))
class CompetitionRoutesModule

trait CompetitionNames extends ComponentNames {
  override val typeName = "competition"
}

