package org.chilternquizleague.web.maintain.competition

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{ Route, RouterModule }

import scala.scalajs.js

import angulate2.http.Http
import org.chilternquizleague.web.service._
import org.chilternquizleague.web.service.text._
import org.chilternquizleague.web.service.competition._
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.web.maintain.component.ComponentNames

import angulate2.common.CommonModule
import org.chilternquizleague.web.maintain.results.ResultsModule
import org.chilternquizleague.web.maintain.fixtures.FixturesModule
import org.chilternquizleague.web.maintain.results.ResultsService
import org.chilternquizleague.web.maintain.fixtures.FixturesService
import angulate2.ext.classModeScala
import org.chilternquizleague.web.maintain.text.TextService


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

@Injectable
@classModeScala
class CompetitionService(
    override val http: Http,
    override val textService: TextService,
    override val resultsService: ResultsService,
    override val fixturesService: FixturesService) extends CompetitionGetService with CompetitionPutService


