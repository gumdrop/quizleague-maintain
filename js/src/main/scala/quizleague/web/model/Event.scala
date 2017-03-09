package quizleague.web.model

import angulate2.std.Data

@Data
case class Event(
  venue: Venue,
  date: String,
  time: String,
  duration : Float)

@Data
case class CalendarEvent(
  venue: Venue,
  date: String,
  time: String,
  duration : Float,
  description: String)
    

