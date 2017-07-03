package quizleague.web.model

import angulate2.std.Data
import quizleague.web.util.rx.RefObservable

@Data
case class Event(
  venue: RefObservable[Venue],
  date: String,
  time: String,
  duration : Float)

@Data
case class CalendarEvent(
  venue: RefObservable[Venue],
  date: String,
  time: String,
  duration : Float,
  description: String)
    

