package quizleague.web.site.results

import quizleague.web.site.fixtures.{FixtureService, FixturesService}
import quizleague.web.site.team.TeamService
import quizleague.web.site.text.TextService
import quizleague.web.site.user.UserService
import quizleague.web.site.fixtures.AllFixturesComponent
import quizleague.web.site.fixtures.AllFixturesTitleComponent
import quizleague.web.site.fixtures.FixturesModule
import quizleague.web.core._
import quizleague.web.core.RouteConfig
import quizleague.web.site.fixtures.AllFixturesTitleComponent
import quizleague.web.site.fixtures.AllFixturesPage
import quizleague.web.site.season.SeasonWatchService
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.site.login.LoginService



object ResultsModule extends Module {

  override val routes = @@(
    RouteConfig(
      path = "/results/all",
      components = Map("default" -> AllResultsPage, "title" -> AllResultsTitleComponent, "sidenav" -> ResultsMenuComponent)),
    RouteConfig(
      path = "/fixtures/all",
      components = Map("default" -> AllFixturesPage, "title" -> AllFixturesTitleComponent, "sidenav" -> ResultsMenuComponent)),
    RouteConfig(
      path = "/results/submit/instructions",
      components = Map("default" -> SubmitResultsInstructionsComponent,  "sidenav" -> ResultsMenuComponent, "title" -> SubmitResultsTitleComponent),
      beforeEnter = LoginService.noAuthRouteGuard _),
    RouteConfig(
      path = "/results/submit",
      components = Map("default" -> SubmitResultsComponent,  "sidenav" -> ResultsMenuComponent, "title" -> SubmitResultsTitleComponent),
      beforeEnter = LoginService.routeGuard _),

    RouteConfig(path = "/results", redirect = "/results/all"))

}



object ResultsViewService extends SeasonWatchService{
  def results = season.flatMap(s => FixturesService.spentFixtures(s.id))
}



