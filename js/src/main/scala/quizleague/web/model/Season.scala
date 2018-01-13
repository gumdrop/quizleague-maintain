package quizleague.web.model


import scalajs.js
import quizleague.web.util.rx.RefObservable


class Season(
    val id:String,
    val startYear:Int,
    val endYear:Int,
    val text:RefObservable[Text],
    val competitions:js.Array[RefObservable[Competition]],
    val calendar:js.Array[CalendarEvent]
) extends js.Object with Model

object Season{
  def apply(    id:String,
    startYear:Int,
    endYear:Int,
    text:RefObservable[Text],
    competitions:js.Array[RefObservable[Competition]],
    calendar:js.Array[CalendarEvent]) = new Season(id,startYear,endYear,text, competitions, calendar)
}