package quizleague.web.model

import angulate2.std.Data
import scalajs.js
import quizleague.web.util.rx.RefObservable

@Data
case class Season(
    id:String,
    startYear:Int,
    endYear:Int,
    text:RefObservable[Text],
    competitions:js.Array[RefObservable[Competition]],
    calendar:js.Array[CalendarEvent]
)