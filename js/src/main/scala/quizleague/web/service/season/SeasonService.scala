package quizleague.web.service.season

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{ Season => Dom }
import quizleague.domain.{ CalendarEvent => DomEvent }
import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.names.ComponentNames
import scala.scalajs.js
import js.JSConverters._

import java.time.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service._
import quizleague.web.service.text._
import quizleague.web.service.competition._
import quizleague.web.names.SeasonNames
import quizleague.web.service.venue.VenueGetService
import quizleague.web.service.venue.VenuePutService

trait SeasonGetService extends GetService[Season] with SeasonNames {
  override type U = Dom
  val textService: TextGetService
  val competitionService: CompetitionGetService
  val venueService: VenueGetService

  override protected def mapOutSparse(season: Dom) = Season(season.id, season.startYear, season.endYear, Text(season.text.id,"",""), refObsList(season.competitions, competitionService),mapEvents(season.calendar))
  override def flush() = { textService.flush(); super.flush() }
  
  private def mapEvents(events:List[DomEvent]):js.Array[CalendarEvent] = {
    events.map(event => CalendarEvent(refObs(event.venue, venueService), event.date,event.time,event.duration,event.description)).toJSArray
  }

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}

trait SeasonPutService extends PutService[Season] with SeasonGetService {
  override val textService: TextPutService
  override val competitionService: CompetitionPutService
  override val venueService:VenuePutService

  override protected def mapIn(season: Season) = Dom(
      season.id, 
      season.startYear, 
      season.endYear, 
      textService.getRef(season.text), 
      competitionService.ref(season.competitions), 
      season.calendar.map(e=>DomEvent(venueService.ref(e.venue), e.date, e.time, e.duration, e.description)).toList
  )
  override protected def make() = Dom(newId(), Year.parse(new Date().getFullYear.toString), Year.parse(new Date().getFullYear.toString) plusYears 1, textService.getRef(textService.instance()), List(),List())

  override def save(season: Season) = { textService.save(season.text); super.save(season) }
  override def flush() = { textService.flush(); super.flush() }

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item: Dom) = item.asJson.noSpaces

}




