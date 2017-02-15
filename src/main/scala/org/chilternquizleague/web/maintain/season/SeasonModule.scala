package org.chilternquizleague.web.maintain.season

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.web.maintain.component.ComponentNames
import org.chilternquizleague.web.service.season._


import angulate2.common.CommonModule
import org.chilternquizleague.web.maintain.competition.CompetitionComponent
import org.chilternquizleague.web.maintain.competition.CompetitionModule
import org.chilternquizleague.web.maintain.competition.CompetitionService
import org.chilternquizleague.web.maintain.competition.LeagueCompetitionComponent
import angulate2.ext.classModeScala
import org.chilternquizleague.web.maintain.text.TextService
import org.chilternquizleague.web.maintain._


@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule,SeasonRoutesModule,CompetitionModule],
  declarations = @@[SeasonComponent,SeasonListComponent],
  providers = @@[SeasonService]
   
)
class SeasonModule

@Routes(
  root = false,
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


