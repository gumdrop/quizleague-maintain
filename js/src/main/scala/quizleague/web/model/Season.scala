package quizleague.web.model


import scalajs.js
import quizleague.web.util.rx.RefObservable
import rxscalajs.Observable


class Season(
    val id:String,
    val startYear:Int,
    val endYear:Int,
    val text:RefObservable[Text],
    val calendar:js.Array[CalendarEvent],
    val competition: Observable[js.Array[Competition]]
) extends Model{
  def toText() = if(startYear==endYear) s"$startYear" else s"$startYear/$endYear"
}

object Season{
  def apply(    id:String,
    startYear:Int,
    endYear:Int,
    text:RefObservable[Text],
    calendar:js.Array[CalendarEvent],
    competition: Observable[js.Array[Competition]]) = new Season(id,startYear,endYear,text, calendar, competition)
}