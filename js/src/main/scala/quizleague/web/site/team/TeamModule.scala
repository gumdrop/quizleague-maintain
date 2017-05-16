package quizleague.web.site.team

import angular.flexlayout.FlexLayoutModule
import angular.material.MaterialModule
import angulate2.common.CommonModule
import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.router.Route
import angulate2.router.RouterModule
import angulate2.std._
import quizleague.web.service.team.TeamGetService
import quizleague.web.site.ServiceRoot
import quizleague.web.site.text.TextModule
import quizleague.web.site.text.TextService
import quizleague.web.site.user.UserService
import quizleague.web.site.venue.VenueService
import quizleague.web.model.Team
import quizleague.web.model.Season
import quizleague.web.site.season._
import quizleague.web.site.common.CommonAppModule
import quizleague.web.site.season.SeasonService
import quizleague.web.site.results.ResultsModule
import quizleague.web.site.results.ResultsComponentsModule
import quizleague.web.site.fixtures.FixturesComponentsModule
import quizleague.web.model.Result
import quizleague.web.model.Fixture
import java.time.LocalDate
import quizleague.web.site.global.ApplicationContextService
import rxjs.Subject
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.util.Logging
import rxjs.BehaviorSubject
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule

@NgModule(
  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, TeamRoutesModule, TextModule, CommonAppModule, ResultsComponentsModule, FixturesComponentsModule, SeasonModule],
  declarations = @@[TeamComponent, TeamsComponent, TeamMenuComponent, TeamTitleComponent, TeamsTitleComponent, TeamResultsComponent, TeamResultsTitleComponent, TeamFixturesComponent, TeamFixturesTitleComponent],
  providers = @@[TeamService, TeamViewService])
class TeamModule

@Routes(
  root = false,
  Route(
    path = "team",
    children = @@@(
      Route(path = ":id", children = @@@(
        Route(path = "", children = @@@(
          Route(path = "", component = %%[TeamComponent]),
          Route(path = "", component = %%[TeamTitleComponent], outlet = "title"))),
        Route(path = "results", children = @@@(
          Route(path = "", component = %%[TeamResultsComponent]),
          Route(path = "", component = %%[TeamResultsTitleComponent], outlet = "title"))),
        Route(path = "fixtures", children = @@@(
          Route(path = "", component = %%[TeamFixturesComponent]),
          Route(path = "", component = %%[TeamFixturesTitleComponent], outlet = "title"))))),
      Route(path = "", children = @@@(
        Route(path = "", component = %%[TeamsComponent]),
        Route(path = "", component = %%[TeamsTitleComponent], outlet = "title"))),
      Route(path = "", component = %%[TeamMenuComponent], outlet = "sidemenu"))))
@classModeScala
class TeamRoutesModule

@Injectable
@classModeScala
class TeamService(override val http: Http,
  override val textService: TextService,
  override val venueService: VenueService,
  override val userService: UserService) extends TeamGetService with ServiceRoot

@Injectable
@classModeScala
class TeamViewService(
    service: TeamService,
    seasonService: SeasonService,
    applicationContextService:ApplicationContextService) extends Logging{

  val season = new BehaviorSubject[Season](null)
  
  season.subscribe(s => log(s, "viewService"))
  
  applicationContextService.get().subscribe(ac => season.next(ac.currentSeason))
  
  
  def getResults(team: Team, season: Season, take: Int = Integer.MAX_VALUE) = seasonService.getResults(season).map(
    (r, i) => r.flatMap(_.results)
      .filter(res => res.fixture.home.id == team.id || res.fixture.away.id == team.id)
      .sort((r1: Result, r2: Result) => r2.fixture.date compareTo r1.fixture.date)
      .take(take))

  def getFixtures(team: Team, season: Season, take: Int = Integer.MAX_VALUE) = {

    val now = LocalDate.now.toString()

    seasonService.getFixtures(season).map(
      (r, i) => r.flatMap(_.fixtures)
        .filter(fixture => fixture.date >= now)
        .filter(fixture => fixture.home.id == team.id || fixture.away.id == team.id)
        .sort((f1: Fixture, f2: Fixture) => f1.date compareTo f2.date)
        .take(take))
  }
}

