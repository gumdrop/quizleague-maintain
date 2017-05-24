package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.std._
import quizleague.web.service.competition.CompetitionGetService
import quizleague.web.site.ServiceRoot
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.site.leaguetable.LeagueTableService
import quizleague.web.site.results.ResultsService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import angulate2.common.CommonModule
import angular.material.MaterialModule
import angulate2.router.RouterModule
import angular.flexlayout.FlexLayoutModule
import quizleague.web.site.text.TextModule
import quizleague.web.site.common.CommonAppModule
import quizleague.web.site.results.ResultsComponentsModule
import quizleague.web.site.fixtures.FixturesComponentsModule
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.season._
import quizleague.web.model.ApplicationContext
import rxjs.ReplaySubject
import quizleague.web.model._
import quizleague.web.site.leaguetable.LeagueTableComponent
import quizleague.web.site.leaguetable.LeagueTableModule



@NgModule(
  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, CompetitionRoutesModule, TextModule, CommonAppModule,ResultsComponentsModule, FixturesComponentsModule, SeasonModule,LeagueTableModule],
  declarations = @@[LeagueCompetitionComponent, 
    CompetitionTitleComponent ,
    CompetitionMenuComponent, 
    CompetitionsComponent, 
    CompetitionsTitleComponent, 
    CompetitionResultsComponent, 
    CompetitionFixturesComponent, 
    CompetitionResultsTitleComponent, 
    CompetitionFixturesTitleComponent,
    CupCompetitionComponent,
    PlateCompetitionComponent,
    BeerCompetitionComponent],
  providers = @@[CompetitionService, CompetitionViewService])
class CompetitionModule


@Routes(
    root = false,
    Route(
        path = "competition",
        children = @@@(
          Route(
            path = ":id",
            children = @@@(
              Route("", children = @@@(
               Route("league", component = %%[LeagueCompetitionComponent]),
               Route("cup", component = %%[CupCompetitionComponent]),
               Route("plate", component = %%[PlateCompetitionComponent]),
               Route("beer", component = %%[BeerCompetitionComponent]),
               Route("", component = %%[CompetitionTitleComponent], outlet = "title")
               )
              ),
              Route(":name", children = @@@(
                Route("results", children = @@@(
                    Route("", component = %%[CompetitionResultsComponent]),
                    Route("", component = %%[CompetitionResultsTitleComponent], outlet = "title")
                    )
                ),
                Route("fixtures", children = @@@(
                    Route("", component = %%[CompetitionFixturesComponent]),
                    Route("", component = %%[CompetitionFixturesTitleComponent], outlet = "title"))
                )
               )
              )
            )
          ) ,
          Route(path = "", children = @@@(
            Route(path = "", component = %%[CompetitionsComponent]),
            Route(path = "", component = %%[CompetitionsTitleComponent], outlet = "title")
          )
         ),
         Route(path = "", component = %%[CompetitionMenuComponent], outlet = "sidemenu")
        )
        
   )
 )
class CompetitionRoutesModule


@Injectable
@classModeScala
class CompetitionService(override val http: Http,
    override val textService: TextService,
    override val resultsService: ResultsService,
    override val fixturesService: FixturesService,
    override val leagueTableService: LeagueTableService,
    override val venueService: VenueService) extends CompetitionGetService with ServiceRoot
    
@Injectable
class CompetitionViewService(
    val service:CompetitionService,
    val applicationContextService:ApplicationContextService,
    val seasonService:SeasonService){
  
    val season = new ReplaySubject[Season](1)

  
    applicationContextService.get().subscribe(ac => season.next(ac.currentSeason))

    
}
