package quizleague.web.service.fixtures



import quizleague.web.model._
import quizleague.web.model.{Fixture => Model, Key}
import quizleague.domain.{Fixture => Dom, Result => DomResult}
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames
import scala.scalajs.js
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service._
import quizleague.web.names.FixtureNames
import quizleague.web.service.venue.VenueGetService
import quizleague.web.service.team.TeamGetService
import quizleague.web.service.venue.VenuePutService
import quizleague.web.service.team.TeamPutService
import quizleague.web.service.DirtyListService
import quizleague.web.names.FixtureNames
import io.circe._,io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import io.circe.Json
import quizleague.web.service.user.UserGetService
import quizleague.web.service.results.ReportGetService
import quizleague.web.util.rx.RefObservable
import rxscalajs.Observable




trait FixtureGetService extends GetService[Fixture] with FixtureNames{
    override type U = Dom
    
  val venueService:VenueGetService
  val teamService:TeamGetService
  val userService:UserGetService
  val reportService:ReportGetService
  val fixturesService:GetService[Fixtures]

  override protected def mapOutSparse(dom:Dom) = Model(
    dom.id,
    venueService.refObs(dom.venue),
    refObs(dom.home, teamService),
    refObs(dom.away, teamService),
    mapResult(dom),
    fixturesService.get(Key(dom.key.get.parentKey.get)))
  
  override protected def dec(json:js.Any) = decodeJson[U](json)
  
  private def mapResult(fixture:Dom):Result = {
       
      fixture.result.fold[Result](null)(r =>
        Result(
          r.homeScore,
          r.awayScore,
          userService.refObs(r.submitter),
          r.note.orNull,
         reportService.list(Key(fixture.key))
        ))
    }

}

trait FixturePutService extends PutService[Fixture] with FixtureGetService{
  override protected def mapIn(model:Model) = Dom(model.id, venueService.refOption(model.venue), teamService.ref(model.home), teamService.ref(model.away), mapInResult(model.result))
  override protected def make() = ???
  
  override val venueService:VenuePutService
  override val teamService:TeamPutService
  
  def instance(fx:Fixtures, home:RefObservable[Team], away:RefObservable[Team], venue:RefObservable[Venue], subsidiary:Boolean) = {

    val dom = withKey(Dom(newId, venueService.refOption(venue),teamService.ref(home),teamService.ref(away),None), fx.key.key)
    mapOutWithKey(dom)
  }
  
  def copy(in:Fixture):Fixture = {
    val fx = mapIn(in)
    val dom = Dom(newId, fx.venue,fx.home,fx.away,None)
    save(dom)
    mapOutWithKey(dom)
  }
  
  private def mapInResult(result:Result):Option[DomResult]  = {
    Option(result).map(r => DomResult(r.homeScore, r.awayScore, Option(userService.ref(r.submitter)), Option(r.note)))
  }
  
  override def enc(item: Dom) = item.asJson

}

