package quizleague.web.site.competition.statistics

import quizleague.web.core._
import quizleague.web.model._
import quizleague.web.site.competition.{CompetitionMenu, CompetitionMenuComponent, CompetitionService}
import quizleague.web.site.season.SeasonService
import quizleague.web.site.team.TeamService
import quizleague.web.model.ResultEntry
import quizleague.web.service.statistics.CompetitionStatisticsGetService
import quizleague.web.site.fixtures.FixturesService.{db, uriRoot}
import rxscalajs.Observable


object CompetitionStatisticsModule extends Module{
  override val routes = @@(
    RouteConfig(path="/competition/rollofhonour/:id", components=Map("default" -> CompetitionStatisticsPage, "title" -> CompetitionStatisticsTitle, "sidenav" -> CompetitionStatisticsMenuComponent)),

       )
      
}


object CompetitionStatisticsService extends CompetitionStatisticsGetService{
  override val teamService = TeamService
  override val seasonService = SeasonService
  override val competitionService = CompetitionService

}

