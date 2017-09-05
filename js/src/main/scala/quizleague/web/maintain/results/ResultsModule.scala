package quizleague.web.maintain.results

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import angular.flexlayout.FlexLayoutModule
import quizleague.web.names.ComponentNames


import angulate2.common.CommonModule
import quizleague.web.service.results._

import angulate2.ext.classModeScala
import quizleague.web.maintain._
import quizleague.web.maintain.fixtures.FixturesService
import quizleague.web.maintain.fixtures.FixtureService
import quizleague.web.maintain.team.TeamService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.user.UserService

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule],
  declarations = @@[ResultsComponent,ResultsListComponent, ReportListComponent],
  providers = @@[ResultsService,ResultService]
   
)
class ResultsModule

@Injectable
@classModeScala
class ResultsService(
    override val http: Http,
    override val fixturesService:FixturesService,
    override val resultService:ResultService) extends ResultsGetService with ResultsPutService with ServiceRoot
    
@Injectable
@classModeScala
class ResultService(
    override val http: Http,
    override val fixtureService:FixtureService,
    override val teamService:TeamService,
    override val userService:UserService,
    override val reportsService:ReportsService
) extends ResultGetService with ResultPutService with ServiceRoot

@Injectable
@classModeScala
class ReportsService(
    override val http: Http,
    override val teamService:TeamService,
    override val textService:TextService
) extends ReportsGetService with ReportsPutService with ServiceRoot


