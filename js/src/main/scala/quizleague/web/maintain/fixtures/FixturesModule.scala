package quizleague.web.maintain.fixtures

import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.fixtures.FixturePutService
import quizleague.web.maintain.team.TeamService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.service.fixtures.FixturesPutService
import quizleague.web.service.results.ReportsGetService
import quizleague.web.service.results.ReportsPutService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.user.UserService
import quizleague.web.maintain.competition.CompetitionService
import rxscalajs.Observable._
import scalajs.js
import js.JSConverters._
import quizleague.web.core._
import quizleague.web.core.RouteConfig
import quizleague.web.maintain.MaintainMenuComponent
import quizleague.web.model.Fixture
import quizleague.domain.Result

object FixturesModule extends Module {
  override val routes = @@(     
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:id/fixtures",
        components = Map("default" -> FixturesListComponent, "sidenav" -> MaintainMenuComponent)
      ),
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:id/fixtures/:fixturesId",
        components = Map("default" -> FixturesComponent, "sidenav" -> MaintainMenuComponent)
      ),
      
  )
  
}

object FixtureService extends FixtureGetService with FixturePutService{
  override val teamService = TeamService
  override val venueService = VenueService
  override val reportsService = ReportsService
  override val userService = UserService
  
  def addResult(fixture:Fixture) = {
    val fx = mapIn(fixture)
    add(mapOutSparse(fx.copy(result = Some(Result(0,0,None,None,None)))))
  }
}

object FixturesService extends FixturesGetService with FixturesPutService{
  override val fixtureService = FixtureService
  
  def fixturesForCompetition(competitionId:String) = CompetitionService.get(competitionId).flatMap(c => combineLatest(c.fixtures.map(_.obs).toSeq)).map(_.toJSArray)
}

object ReportsService extends ReportsGetService with ReportsPutService{
  override val teamService = TeamService
  override val textService = TextService
}