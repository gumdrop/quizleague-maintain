package quizleague.web.maintain.season

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import quizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import quizleague.web.maintain.component.ComponentNames
import quizleague.web.service.season._


import angulate2.common.CommonModule
import quizleague.web.maintain.competition.CompetitionComponent
import quizleague.web.maintain.competition.CompetitionModule
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.competition.LeagueCompetitionComponent
import angulate2.ext.classModeScala
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain._
import quizleague.web.maintain.fixtures.FixturesListComponent
import quizleague.web.maintain.fixtures.FixturesComponent


@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule,SeasonRoutesModule,CompetitionModule],
  declarations = @@[SeasonComponent,SeasonListComponent],
  providers = @@[SeasonService]
   
)
class SeasonModule

@Routes(
  root = false,
      
      Route(
        path = "season/:seasonId/competition/:competitionId/:type/fixtures/:id",
        component = %%[FixturesComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:competitionId/:type/fixtures",
        component = %%[FixturesListComponent]
      ),
      Route(
        path = "season/:seasonId/competition/:id/league",
        component = %%[LeagueCompetitionComponent]
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

trait SeasonNames extends ComponentNames{
  override val typeName = "season"
}

@Injectable
@classModeScala
class SeasonService(override val http: Http, 
    override val textService: TextService, 
    override val competitionService: CompetitionService) extends SeasonGetService with SeasonPutService with ServiceRoot 


