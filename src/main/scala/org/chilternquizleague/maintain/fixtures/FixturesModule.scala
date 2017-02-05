package org.chilternquizleague.maintain.fixtures

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.maintain.component.ComponentNames


import angulate2.common.CommonModule
import org.chilternquizleague.maintain.competition.CompetitionComponent
import org.chilternquizleague.maintain.competition.CompetitionModule
import org.chilternquizleague.maintain.competition.CompetitionService


@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule],
//  declarations = @@[SeasonComponent,SeasonListComponent],
  providers = @@[FixturesService]
   
)
class FixturesModule

//@Routes(
//  root = false,
//      Route(
//        path = "results/:id",
//        component = %%[SeasonComponent]
//      )
//)
//class ResultsRoutesModule 

trait FixturesNames extends ComponentNames{
  override val typeName = "fixtures"
}

