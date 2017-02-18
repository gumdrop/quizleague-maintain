package quizleague.web.model

import java.time.LocalTime
import java.time.LocalDate
import scala.scalajs.js.Date

case class Event(
  venue: Venue,
  date: Date,
  time: Date)

case class CalendarEvent(
  venue: Venue,
  date: Date,
  time: Date,
  description: String)
    

