package quizleague.web.model

import angulate2.std.Data
import scalajs.js

@Data
case class Season(
    id:String,
    startYear:Int,
    endYear:Int,
    text:Text,
    competitions:js.Array[Competition],
    calendar:js.Array[CalendarEvent]
)