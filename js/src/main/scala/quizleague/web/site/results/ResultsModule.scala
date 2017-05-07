package quizleague.web.site.results

import angular.flexlayout.FlexLayoutModule
import angular.material.MaterialModule
import angulate2.common.CommonModule
import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.router.{ Route, RouterModule }
import angulate2.std._
import quizleague.web.service.results.{ ResultGetService, ResultsGetService }
import quizleague.web.site.ServiceRoot
import quizleague.web.site.fixtures.{ FixtureService, FixturesService }
import quizleague.web.site.team.TeamService
import quizleague.web.site.text.TextService
import quizleague.web.site.user.UserService
import quizleague.web.site.common.CommonAppModule

@NgModule(
  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, ResultsRoutesModule,CommonAppModule],
  declarations = @@[AllResultsComponent, AllResultsTitleComponent, ResultsMenuComponent, AllFixturesComponent, AllFixturesTitleComponent,SimpleResultsComponent],
  providers = @@[ResultsService, ResultService])
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
      Route(path = "",   redirectTo = "all", pathMatch = "full" )))
 
)
@classModeScala
class ResultsRoutesModule

@Injectable
@classModeScala
class ResultsService(override val http: Http,
    override val resultService: ResultService,
    override val fixturesService: FixturesService) extends ResultsGetService with ServiceRoot {

}

@Injectable
@classModeScala
class ResultService(override val http: Http,
    val userService: UserService,
    val fixtureService: FixtureService,
    val textService: TextService,
    val teamService: TeamService) extends ResultGetService with ServiceRoot {

}

