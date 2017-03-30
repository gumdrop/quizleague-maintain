package quizleague.web.service.fixtures


import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{Fixture => Model}
import quizleague.domain.{Fixture => Dom}
import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.names.ComponentNames
import scala.scalajs.js
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service._
import quizleague.web.names.FixtureNames
import rxjs.Observable
import quizleague.web.service.venue.VenueGetService
import quizleague.web.service.team.TeamGetService
import quizleague.web.service.venue.VenuePutService
import quizleague.web.service.team.TeamPutService
import quizleague.web.service.DirtyListService
import quizleague.web.names.FixtureNames



trait FixtureGetService extends GetService[Fixture] with FixtureNames{
    override type U = Dom
    
  val venueService:VenueGetService
  val teamService:TeamGetService

  override protected def mapOutSparse(dom:Dom) = Model(dom.id,dom.description,dom.parentDescription,null,null,null,dom.date,dom.time,dom.duration)
  override protected def mapOut(dom: Dom)(implicit depth:Int) =
    Observable.zip(
      child(dom.venue,venueService),
      child(dom.home,teamService),
      child(dom.away, teamService),
      (venue: Venue, home: Team, away: Team) => Model(dom.id, dom.description, dom.parentDescription, venue, home, away, dom.date, dom.time, dom.duration))

  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]
 
}

trait FixturePutService extends PutService[Fixture] with FixtureGetService with DirtyListService[Model]{
  override protected def mapIn(model:Model) = Dom(model.id, model.description, model.parentDescription, venueService.getRef(model.venue), teamService.getRef(model.home), teamService.getRef(model.away), model.date, model.time, model.duration)
  override protected def make() = ???
  
  override val venueService:VenuePutService
  override val teamService:TeamPutService
  
  def instance(fx:Fixtures, home:Team, away:Team, venue:Venue) = {
    val dom = Dom(newId,fx.description, fx.parentDescription,venueService.getRef(venue),teamService.getRef(home),teamService.getRef(away),fx.date,fx.start,fx.duration)
    mapOut(dom)(1)
  }
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item:Dom) = item.asJson.noSpaces

}

