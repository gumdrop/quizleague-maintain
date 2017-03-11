package quizleague.web.maintain.competition

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{ Route, RouterModule }

import scala.scalajs.js

import angulate2.http.Http
import quizleague.web.service._
import quizleague.web.service.text._
import quizleague.web.service.competition._
import angular.flexlayout.FlexLayoutModule
import quizleague.web.maintain.component.ComponentNames
import quizleague.web.maintain._

import angulate2.common.CommonModule
import quizleague.web.maintain.results.ResultsModule
import quizleague.web.maintain.fixtures.FixturesModule
import quizleague.web.maintain.results.ResultsService
import quizleague.web.maintain.fixtures.FixturesService
import angulate2.ext.classModeScala
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.leaguetable.LeagueTableModule
import quizleague.web.maintain.leaguetable.LeagueTableService
import quizleague.web.maintain.venue.VenueService


@NgModule(
  imports = @@[CommonModule, FormsModule, MaterialModule, RouterModule, FlexLayoutModule, FixturesModule,ResultsModule,LeagueTableModule],
  declarations = @@[LeagueCompetitionComponent,CupCompetitionComponent, SubsidiaryCompetitionComponent, SingletonCompetitionComponent],
  providers = @@[CompetitionService])
class CompetitionModule

//@Routes(
//  root = false,
//  Route(
//    path = "_competition/:id",
//    component = %%[CompetitionComponent]))
//class CompetitionRoutesModule

trait CompetitionNames extends ComponentNames {
  override val typeName = "competition"
}

@Injectable
@classModeScala
class CompetitionService(
    override val http: Http,
    override val textService: TextService,
    override val resultsService: ResultsService,
    override val fixturesService: FixturesService,
    override val leagueTableService:LeagueTableService,
    override val venueService:VenueService) extends CompetitionGetService with CompetitionPutService with ServiceRoot


