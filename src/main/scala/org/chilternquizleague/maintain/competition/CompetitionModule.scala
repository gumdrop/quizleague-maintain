package org.chilternquizleague.maintain.competition

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{ Route, RouterModule }

import scala.scalajs.js

import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.maintain.component.ComponentNames

import angulate2.common.CommonModule

@NgModule(
  imports = @@[CommonModule, FormsModule, MaterialModule, RouterModule, FlexLayoutModule, CompetitionRoutesModule],
  declarations = @@[CompetitionComponent],
  providers = @@[CompetitionService])
class SeasonModule

@Routes(
  root = false,
  Route(
    path = "competition/:id",
    component = %%[CompetitionComponent]))
class CompetitionRoutesModule

trait CompetitionNames extends ComponentNames {
  override val typeName = "competition"
}

