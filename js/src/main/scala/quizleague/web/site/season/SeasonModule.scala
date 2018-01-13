package quizleague.web.site.season

import quizleague.web.service.season.SeasonGetService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.site.competition.CompetitionService
import quizleague.web.core._

object SeasonModule extends Module {
  override val components = @@(SeasonSelectComponent)
}

object SeasonService extends SeasonGetService{
  
  override val textService = TextService
  override val venueService = VenueService
  override val competitionService = CompetitionService
  
}