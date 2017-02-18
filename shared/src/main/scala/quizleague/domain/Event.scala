package quizleague.domain

import java.time.LocalTime
import java.time.LocalDate

sealed trait BaseEvent {
  val venue: Ref[Venue]
  val date: LocalDate
  val time: LocalTime
}

case class Event(
  venue: Ref[Venue],
  date: LocalDate,
  time: LocalTime) extends BaseEvent

case class CalendarEvent(
  venue: Ref[Venue],
  date: LocalDate,
  time: LocalTime,
  description: String) extends BaseEvent
    

