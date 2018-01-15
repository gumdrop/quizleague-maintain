package quizleague.web.model

import quizleague.web.util.rx.RefObservable
import scalajs.js

class Event(
  val venue: RefObservable[Venue],
  val date: String,
  val time: String,
  val duration: Float) extends js.Object

object Event {
  def apply(
    venue: RefObservable[Venue],
    date: String,
    time: String,
    duration: Float) = new Event(venue, date, time, duration)
}

class CalendarEvent(
  val venue: RefObservable[Venue],
  val date: String,
  val time: String,
  val duration: Float,
  val description: String) extends js.Object

object CalendarEvent {
  def apply(
    venue: RefObservable[Venue],
    date: String,
    time: String,
    duration: Float,
    description: String) = new CalendarEvent(venue, date, time, duration, description)
}
    

