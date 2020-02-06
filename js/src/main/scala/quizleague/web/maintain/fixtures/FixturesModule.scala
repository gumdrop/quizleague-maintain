package quizleague.web.maintain.fixtures

import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.fixtures.FixturePutService
import quizleague.web.maintain.team.TeamService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.service.fixtures.FixturesPutService
import quizleague.web.service.results.ReportsGetService
import quizleague.web.service.results.ReportsPutService
import quizleague.web.service.results.ReportGetService
import quizleague.web.service.results.ReportPutService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.user.UserService
import quizleague.web.maintain.competition.CompetitionService
import rxscalajs.Observable._

import scalajs.js
import js.JSConverters._
import quizleague.web.core._
import quizleague.web.core.RouteConfig
import quizleague.web.maintain.MaintainMenuComponent
import quizleague.web.model._
import quizleague.domain.Result
import quizleague.web.maintain.chat.ChatService
import quizleague.web.util.rx.RefObservable
import rxscalajs.Observable

object FixturesModule extends Module {
  override val routes = @@(     
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:id/fixtures",
        components = Map("default" -> FixturesListComponent)
      ),
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:id/fixtures/:fixturesId",
        components = Map("default" -> FixturesComponent)
      ),
      
  )
  
}

object FixtureService extends FixtureGetService with FixturePutService{
  override val teamService = TeamService
  override val venueService = VenueService
  override val reportsService = ReportsService
  override val userService = UserService
  override val fixturesService = FixturesService
  override val reportService = ReportService
  
  def addResult(fixture:Fixture) = {
    val fx = mapIn(fixture)
    add(mapOutSparse(fx.copy(result = Some(Result(0,0,None,None,None)))))
  }
  
  def copy(fxs:Fixtures, parentDescription:String, subsidiary:Boolean):Observable[js.Array[RefObservable[Fixture]]] = {
    combineLatest(fxs.fixtures.map(_.obs.map(fx => {
      val x = copy(fx, parentDescription,subsidiary)
      getRefObs(x.id)
    }))).map(_.toJSArray)
  }
  
}

object FixturesService extends FixturesGetService with FixturesPutService{
  override val fixtureService = FixtureService
  
  def fixturesForCompetition(competitionId:String) = CompetitionService.get(competitionId).flatMap(c => combineLatest(c.fixtures.map(_.obs).toSeq)).map(_.toJSArray)

  def copy(fixtures:js.Array[RefObservable[Fixtures]], parentDescription:String, subsidiary:Boolean):Observable[js.Array[RefObservable[Fixtures]]] = {
    Observable.combineLatest(fixtures.map(f => copy(f,parentDescription, subsidiary)).toSeq).map(_.toJSArray)
  }
  
  private def copy(fixtures:RefObservable[Fixtures], parentDescription:String, subsidiary:Boolean):Observable[RefObservable[Fixtures]] = {
    
    val model = get(fixtures.id)
    
    model.flatMap(fxs => Observable.of(fxs).combineLatest(fixtureService.copy(fxs, parentDescription, subsidiary)).map(x => {
      val f = copy(fxs,parentDescription,x._2, true)
      getRefObs(f.id)
    }))
    
  }

}

object ReportsService extends ReportsGetService with ReportsPutService{
  override val teamService = TeamService
  override val textService = TextService
  override val chatService = ChatService
}

object ReportService extends ReportGetService with ReportPutService{
  override val teamService = TeamService
  override val textService = TextService
}

