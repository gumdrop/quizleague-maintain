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
import org.threeten.bp.LocalDate
import quizleague.web.site.global.ApplicationContextService
import rxjs.Subject
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.util.Logging
import rxjs.BehaviorSubject
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import quizleague.web.site.common.SeasonSelectService
import quizleague.web.util.rx._
import quizleague.web.service.CachingService
import quizleague.web.service.CachingService

@NgModule(
  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, TeamRoutesModule, TextModule, CommonAppModule, ResultsComponentsModule, FixturesComponentsModule, SeasonModule],
  declarations = @@[TeamComponent, TeamsComponent, TeamMenuComponent, TeamTitleComponent, TeamsTitleComponent, TeamSubTitleComponent, TeamResultsComponent, TeamResultsTitleComponent, TeamFixturesComponent, TeamFixturesTitleComponent],
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
  override val userService: UserService) extends TeamGetService with ServiceRoot with CachingService[Team]

@Injectable
@classModeScala
class TeamViewService(
    service: TeamService,
    seasonService: SeasonService,
    override val applicationContextService:ApplicationContextService) extends SeasonSelectService {


  def getResults(team: Team, season: Season, take: Int = Integer.MAX_VALUE) = 
    seasonService.getResults(season).switchMap(
    (r, i) => 
      filterAndSort[Result,Fixture](
          r.flatMap(_.results),
          r => r.fixture,
          (r,f) => f.home.id == team.id || f.away.id == team.id, 
          (r1,r2) => r2._2.date compareTo r1._2.date)
          .map((r,i) => r.take(take)))


  def getFixtures(team: Team, season: Season, take: Int = Integer.MAX_VALUE) = {

    val now = LocalDate.now.toString()

    seasonService.getFixtures(season).map(
      (r, i) => filter[Fixture](r.flatMap(_.fixtures), f => f.date >= now && (f.home.id == team.id || f.away.id == team.id))
       .map((f,i) => f.take(take))).concatAll()
  }
}

