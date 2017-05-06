package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.std._
import quizleague.web.service.competition.CompetitionGetService
import quizleague.web.site.ServiceRoot
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.site.leaguetable.LeagueTableService
import quizleague.web.site.results.ResultsService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService



@NgModule(
  providers = @@[CompetitionService])
class CompetitionModule

@Injectable
@classModeScala
class CompetitionService(override val http: Http,
    override val textService: TextService,
    override val resultsService: ResultsService,
    override val fixturesService: FixturesService,
    override val leagueTableService: LeagueTableService,
    override val venueService: VenueService) extends CompetitionGetService with ServiceRoot {

}

