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
import quizleague.web.util.rx._
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
  
    def getResults(season:Season):Observable[js.Array[Results]] = zip(season.competitions).switchMap((c,i) => 
        resWithFixs(c.flatMap(_.results))
        .map((rs,i) => rs.sort(sortPair _)
            .map(_._1)))
    
    def getFixtures(season:Season) = zip(season.competitions).map((c,i) => c.flatMap(_.fixtures).sort((f1:Fixtures,f2:Fixtures) => f1.date compareTo f2.date))
    
    def getLeagueCompetition(season:Season) = zip(season.competitions).map((cs,i) => cs.filter(_.typeName == "league").head)

  
    private def resWithFixs(results:js.Array[Results]):Observable[js.Array[(Results,Fixtures)]] = Observable.zip(results.map(r => r.fixtures.obs.map((f,i) => (r,f))):_*)
    
    private def sortPair(r1:(Results,Fixtures), r2:(Results,Fixtures)) = r2._2.date compareTo r1._2.date
}
