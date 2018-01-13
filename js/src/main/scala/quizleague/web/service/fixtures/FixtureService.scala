package quizleague.web.service.fixtures



import quizleague.web.model._
import quizleague.web.model.{Fixture => Model}
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
import quizleague.web.service.results.ReportsGetService
import quizleague.web.util.rx.RefObservable




trait FixtureGetService extends GetService[Fixture] with FixtureNames{
    override type U = Dom
    
  val venueService:VenueGetService
  val teamService:TeamGetService
  val userService:UserGetService
  val reportsService:ReportsGetService

  override protected def mapOutSparse(dom:Dom) = Model(dom.id,dom.description,dom.parentDescription,refObs(dom.venue, venueService),refObs(dom.home, teamService),refObs(dom.away, teamService),dom.date,dom.time,dom.duration, mapResult(dom.result))
  
  override protected def dec(json:js.Any) = decodeJson[U](json)
  
  private def mapResult(dom:Option[DomResult]):Result = {
       
      dom.fold[Result](null)(r => Result(r.homeScore, r.awayScore, userService.refObs(r.submitter), r.note.orNull, reportsService.refObs(r.reports))) 
    }

}

trait FixturePutService extends PutService[Fixture] with FixtureGetService with DirtyListService[Model]{
  override protected def mapIn(model:Model) = Dom(model.id, model.description, model.parentDescription, venueService.ref(model.venue), teamService.ref(model.home), teamService.ref(model.away), model.date, model.time, model.duration, mapInResult(model.result))
  override protected def make() = ???
  
  override val venueService:VenuePutService
  override val teamService:TeamPutService
  
  def instance(fx:Fixtures, home:RefObservable[Team], away:RefObservable[Team], venue:RefObservable[Venue]) = {
    val dom = Dom(newId,fx.description, fx.parentDescription,venueService.ref(venue),teamService.ref(home),teamService.ref(away),fx.date,fx.start,fx.duration, None)
    mapOutSparse(dom)
  }
  
  private def mapInResult(r:Result):Option[DomResult]  = {
    if(r != null) {
      Some(DomResult(r.homeScore, r.awayScore, Option(userService.ref(r.submitter)), Option(r.note), reportsService.refOption(r.reports)))
    }
    else None
  }
  
  override def enc(item: Dom) = item.asJson

}

