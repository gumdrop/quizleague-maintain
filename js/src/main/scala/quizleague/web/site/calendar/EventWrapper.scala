package quizleague.web.site.calendar

import quizleague.web.model._
import scala.scalajs.js.annotation.JSExportAll
import scala.scalajs.js.annotation.JSExport
import scalajs.js



trait EventWrapper{
  @JSExport
  val eventType:String
  
  @JSExport
  val date:String
}


class CalendarEventWrapper(@JSExport val event:CalendarEvent) extends EventWrapper{
  override val eventType = "calendar"
  override val date = if(event == null) null else event.date
}

@JSExportAll
class CompetitionEventWrapper(val event:Event, val competition:Competition) extends EventWrapper{
  override val eventType = "competition"
  override val date = if(event == null) null else event.date
}


@JSExportAll
class FixturesEventWrapper(val fixtures:Fixtures, val competition:Competition) extends EventWrapper{
  override val eventType = "fixtures"
  override val date = fixtures.date
}

object EventWrapper{
  
  def apply(event:Event, competition:Competition):EventWrapper = new CompetitionEventWrapper(event, competition)
  def apply(event:CalendarEvent):EventWrapper  = new CalendarEventWrapper(event)
  def apply(fixtures:Fixtures, competition:Competition):EventWrapper = new FixturesEventWrapper(fixtures, competition)
  
}