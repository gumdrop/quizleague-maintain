package quizleague.web.site.season

import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.std._
import quizleague.web.model.Season
import quizleague.web.service.season.SeasonGetService
import quizleague.web.site.ServiceRoot
import quizleague.web.site.competition.CompetitionService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.model.Team
import angulate2.common.CommonModule
import angulate2.forms.FormsModule
import angular.material.MaterialModule
import quizleague.web.model.Results
import quizleague.web.model.Fixtures
import scalajs.js
import quizleague.web.util.rx.RefObservable
import rxjs.Observable


@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule],
  declarations = @@[SeasonSelectComponent,SeasonNameComponent],
  providers = @@[SeasonService],
  exports = @@[SeasonSelectComponent,SeasonNameComponent]
)
class SeasonModule


@Injectable
@classModeScala
class SeasonService(override val http: Http,
    override val textService: TextService,
    override val competitionService: CompetitionService,
    override val venueService: VenueService) extends SeasonGetService with ServiceRoot {
  
    def getResults(season:Season) = zip(season.competitions).map((c,i) => c.flatMap(_.results).sort((r1:Results,r2:Results) => r2.fixtures.date compareTo r1.fixtures.date))
    
    def getFixtures(season:Season) = zip(season.competitions).map((c,i) => c.flatMap(_.fixtures).sort((f1:Fixtures,f2:Fixtures) => f1.date compareTo f2.date))
    
    def getLeagueCompetition(season:Season) = zip(season.competitions).map((cs,i) => cs.filter(_.typeName == "league").head)

  
}
