package quizleague.web.model

import java.time.LocalTime
import java.time.LocalDate
import scala.scalajs.js.Date

case class Event(
  venue: Venue,
  date: String,
  time: String,
  duration : Float)

case class CalendarEvent(
  venue: Venue,
  date: String,
  time: String,
  duration : Float,
  description: String)
    

