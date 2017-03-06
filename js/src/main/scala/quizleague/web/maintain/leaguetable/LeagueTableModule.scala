package quizleague.web.maintain.leaguetable

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import angular.flexlayout.FlexLayoutModule
import quizleague.web.maintain.component.ComponentNames


import angulate2.common.CommonModule
import quizleague.web.service.leaguetable._

import angulate2.ext.classModeScala
import quizleague.web.maintain._
import quizleague.web.maintain.fixtures.FixturesService
import quizleague.web.maintain.fixtures.FixtureService
import quizleague.web.maintain.team.TeamService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.user.UserService
import quizleague.web.maintain.results.ResultsService

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule],
  declarations = @@[LeagueTableComponent,LeagueTableListComponent],
  providers = @@[LeagueTableService]
   
)
class LeagueTableModule

trait LeagueTableNames extends ComponentNames{
  override val typeName = "leaguetable"
}


@Injectable
@classModeScala
class LeagueTableService(
    override val http: Http,
    override val teamService:TeamService,
    override val resultsService:ResultsService
) extends LeagueTableGetService with LeagueTablePutService with ServiceRoot


