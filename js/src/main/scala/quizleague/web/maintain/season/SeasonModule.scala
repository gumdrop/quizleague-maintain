package quizleague.web.maintain.season

import quizleague.web.service.season.SeasonGetService
import quizleague.web.service.season.SeasonPutService
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.core._
import quizleague.web.core.RouteConfig
import quizleague.web.maintain.MaintainMenuComponent
import quizleague.web.maintain.competition.CompetitionModule

object SeasonModule extends Module {
  
  override val modules = @@(CompetitionModule)
  
  override val routes = @@(     
      RouteConfig(
        path = "/maintain/season/:id/calendar",
        components = Map("default" -> CalendarComponent)
      ),
      RouteConfig(
        path = "/maintain/season/:id",
        components = Map("default" -> SeasonComponent)
      ),
      RouteConfig(
        path = "/maintain/season",
        components = Map("default" -> SeasonListComponent)
      ))
}

object SeasonService extends SeasonGetService with SeasonPutService{
  override val competitionService = CompetitionService
  override val textService = TextService
  override val venueService = VenueService
}