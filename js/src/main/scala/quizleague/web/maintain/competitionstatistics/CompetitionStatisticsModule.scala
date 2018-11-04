package quizleague.web.maintain.competitionstatistics

import quizleague.web.core._
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.season.SeasonService
import quizleague.web.maintain.team.TeamService
import quizleague.web.model.ResultEntry
import quizleague.web.service.statistics.{CompetitionStatisticsGetService, CompetitionStatisticsPutService}


object CompetitionStatisticsModule extends Module{
  override val routes = @@(
      RouteConfig(path = "/maintain/competitionstatistics", components = Map("default" -> CompetitionStatisticsListComponent)),
      RouteConfig(path="/maintain/competitionstatistics/:id", components = Map("default" -> CompetitionStatisticsComponent))
       )
      
}


object CompetitionStatisticsService extends CompetitionStatisticsGetService with CompetitionStatisticsPutService{
  override val teamService = TeamService
  override val seasonService = SeasonService
  override val competitionService = CompetitionService

  def resultEntryInstance() = new ResultEntry(null,null,null,null,null)

}

