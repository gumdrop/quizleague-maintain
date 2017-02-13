package org.chilternquizleague.web.maintain.fixtures

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.web.maintain.component.ComponentNames


import angulate2.common.CommonModule
import org.chilternquizleague.web.maintain.competition.CompetitionComponent
import org.chilternquizleague.web.maintain.competition.CompetitionModule
import org.chilternquizleague.web.service.fixtures._
import angulate2.ext.classModeScala

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

@Injectable
@classModeScala
class FixturesService(override val http:Http) extends FixturesGetService with FixturesPutService


