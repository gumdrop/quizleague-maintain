package org.chilternquizleague.maintain.season

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
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule,SeasonRoutesModule,CompetitionModule],
  declarations = @@[SeasonComponent,SeasonListComponent],
  providers = @@[SeasonService]
   
)
class SeasonModule

@Routes(
  root = false,
//      Route(
//        path = "season/:seasonId/competition/:id",
//        component = %%[CompetitionComponent]
//      ),
      Route(
        path = "season/:id",
        component = %%[SeasonComponent]
      ),
      Route(
        path = "season",
        component = %%[SeasonListComponent]
      )
)
class SeasonRoutesModule 

trait SeasonNames extends ComponentNames{
  override val typeName = "season"
}

