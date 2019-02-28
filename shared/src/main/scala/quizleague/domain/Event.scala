package quizleague.domain

import java.time.LocalTime
import java.time.LocalDate
import java.time.Duration

trait BaseEvent {
  val venue: Option[Ref[Venue]]
  val date: LocalDate
  val time: LocalTime
  val duration :Duration
}

case class Event(
  venue: Option[Ref[Venue]],
  date: LocalDate,
  time: LocalTime,
  duration : Duration) extends BaseEvent

case class CalendarEvent(
  venue: Option[Ref[Venue]],
  date: LocalDate,
  time: LocalTime,
  duration : Duration,
  description: String) extends BaseEvent
    

