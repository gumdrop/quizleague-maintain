package quizleague.web.maintain.results

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import angular.flexlayout.FlexLayoutModule
import quizleague.web.maintain.component.ComponentNames


import angulate2.common.CommonModule
import quizleague.web.service.results._

import angulate2.ext.classModeScala
import quizleague.web.maintain._
import quizleague.web.maintain.fixtures.FixturesService

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule],
//  declarations = @@[SeasonComponent,SeasonListComponent],
  providers = @@[ResultsService]
   
)
class ResultsModule

trait ResultsNames extends ComponentNames{
  override val typeName = "results"
}

trait ResultNames extends ComponentNames{
  override val typeName = "result"
}

@Injectable
@classModeScala
class ResultsService(
    override val http: Http,
    override val fixturesService:FixturesService,
    override val resultService:ResultService) extends ResultsGetService with ResultsPutService with ServiceRoot
    
@Injectable
@classModeScala
class ResultService(
    override val http: Http) extends ResultGetService with ResultPutService with ServiceRoot


