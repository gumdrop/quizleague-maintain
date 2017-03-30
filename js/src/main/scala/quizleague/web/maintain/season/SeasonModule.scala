package quizleague.web.maintain.season

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import quizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import quizleague.web.names.ComponentNames
import quizleague.web.service.season._


import angulate2.common.CommonModule
import quizleague.web.maintain.competition.CompetitionModule
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.competition.LeagueCompetitionComponent
import angulate2.ext.classModeScala
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain._
import quizleague.web.maintain.fixtures.FixturesListComponent
import quizleague.web.maintain.fixtures.FixturesComponent
import quizleague.web.maintain.results.ResultsComponent
import quizleague.web.maintain.results.ResultsListComponent
import quizleague.web.maintain.leaguetable.LeagueTableComponent
import quizleague.web.maintain.leaguetable.LeagueTableListComponent
import quizleague.web.maintain.results.ReportListComponent
import quizleague.web.maintain.venue.VenueService
import quizleague.web.maintain.competition.CupCompetitionComponent
import quizleague.web.maintain.competition.SubsidiaryCompetitionComponent
import quizleague.web.maintain.competition.SingletonCompetitionComponent


@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule,SeasonRoutesModule,CompetitionModule],
  declarations = @@[SeasonComponent,SeasonListComponent, CalendarComponent],
  providers = @@[SeasonService]
   
)
class SeasonModule

@Routes(
  root = false,
      
      
      Route(
        path = "season/:seasonId/competition/:competitionId/:type/results/:resultsId/result/:id/report",
        component = %%[ReportListComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:competitionId/:type/fixtures/:id",
        component = %%[FixturesComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:competitionId/:type/results/:id",
        component = %%[ResultsComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:competitionId/:type/leaguetable/:id",
        component = %%[LeagueTableComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:competitionId/:type/fixtures",
        component = %%[FixturesListComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:competitionId/:type/results",
        component = %%[ResultsListComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:competitionId/:type/leaguetable",
        component = %%[LeagueTableListComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:id/league",
        component = %%[LeagueCompetitionComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:id/cup",
        component = %%[CupCompetitionComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:id/subsidiary",
        component = %%[SubsidiaryCompetitionComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:id/singleton",
        component = %%[SingletonCompetitionComponent]
      ),
      Route(
        path = "season/:id/calendar",
        component = %%[CalendarComponent]
      ),
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


@Injectable
@classModeScala
class SeasonService(override val http: Http, 
    override val textService: TextService, 
    override val competitionService: CompetitionService,
    override val venueService:VenueService) extends SeasonGetService with SeasonPutService with ServiceRoot 


