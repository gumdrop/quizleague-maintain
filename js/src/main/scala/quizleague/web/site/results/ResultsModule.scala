package quizleague.web.site.results

import angular.flexlayout.FlexLayoutModule
import angular.material.MaterialModule
import angulate2.common.CommonModule
import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.router.{ Route, RouterModule }
import angulate2.std._
import quizleague.web.service.results.{ ResultGetService, ResultsGetService, ReportsGetService }
import quizleague.web.site.ServiceRoot
import quizleague.web.site.fixtures.{ FixtureService, FixturesService }
import quizleague.web.site.team.TeamService
import quizleague.web.site.text.TextService
import quizleague.web.site.user.UserService
import quizleague.web.site.common.CommonAppModule
import quizleague.web.site.fixtures.AllFixturesComponent
import quizleague.web.site.fixtures.AllFixturesTitleComponent
import quizleague.web.site.fixtures.FixturesModule
import quizleague.web.site.global.ApplicationContextService
import rxjs._
import quizleague.web.model.Season
import quizleague.web.site.season.SeasonModule
import quizleague.web.site.text.TextModule
import quizleague.web.util.Logging._
import quizleague.web.site.common.SeasonSelectService
import angulate2.ext.classModeJS
import quizleague.web.service.CachingService
import quizleague.web.model.Result
import quizleague.web.model.Team
import inviewport.InViewportModule

@NgModule(
  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, ResultsRoutesModule,CommonAppModule, TextModule, FixturesModule, ResultsComponentsModule, SeasonModule],
  declarations = @@[AllResultsComponent, AllResultsTitleComponent, ResultsMenuComponent, ReportComponent, ReportTitleComponent],
  providers = @@[ResultsService, ResultService, ResultsViewService])
class ResultsModule

@Routes(
  root = false,
  Route(
    path = "results",
    children = @@@(
      Route(path = "all", children = @@@(
        Route(path = "", component = %%[AllResultsComponent]),
        Route(path = "", component = %%[AllResultsTitleComponent], outlet = "title"))),
      Route(path = "fixtures", children = @@@(
        Route(path = "", component = %%[AllFixturesComponent]),
        Route(path = "", component = %%[AllFixturesTitleComponent], outlet = "title"))),
      Route(path = "", component = %%[ResultsMenuComponent], outlet = "sidemenu"),
      Route(path = ":id", children = @@@(
          Route("reports", children = @@@(
            Route("", component = %%[ReportComponent]),
            Route("", component = %%[ReportTitleComponent], outlet="title")
          )))),
      Route(path = "",   redirectTo = "all", pathMatch = "full" )  
    ))
 
)
@classModeScala
class ResultsRoutesModule

@NgModule(
  imports = @@[CommonModule,RouterModule,MaterialModule,InViewportModule],
  declarations = @@[SimpleResultsComponent],
  exports = @@[SimpleResultsComponent]
  )
class ResultsComponentsModule

@Injectable
@classModeScala
class ResultsService(override val http: Http,
    override val resultService: ResultService,
    override val fixturesService: FixturesService) extends ResultsGetService with ServiceRoot {
  
  def latestResults(season:Season) = list(Some(s"latest/${season.id}"))

}

@Injectable
@classModeScala
class ResultService(override val http: Http,
    val userService: UserService,
    val fixtureService: FixtureService,
    val teamService: TeamService,
    val reportsService: ReportsService) extends ResultGetService with ServiceRoot with CachingService[Result]{

  def teamResults(season:Season,team:Team, take:Int = Integer.MAX_VALUE) = list(Some(s"season/${season.id}/team/${team.id}?take=$take"))
}

@Injectable
@classModeScala
class ReportsService(override val http: Http,
    val textService: TextService,
    val teamService: TeamService) extends ReportsGetService with ServiceRoot 


@Injectable
@classModeScala
class ResultsViewService (
  override val applicationContextService:ApplicationContextService 
)extends SeasonSelectService

