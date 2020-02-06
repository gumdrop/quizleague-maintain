package quizleague.web.model

import scala.scalajs.js
import quizleague.web.util.rx.RefObservable
import quizleague.web.util.UUID
import rxscalajs.Observable


class Fixtures(
    val id:String, 
    val description:String,
    val parentDescription:String,
    val date:String,
    val start:String,
    val duration:Float,
    val fixtures:js.Array[RefObservable[Fixture]],
    val fixture: Observable[js.Array[Fixture]],
    val subsidiary:Boolean) extends Model
    
object Fixtures{
  def apply(id:String, 
    description:String,
    parentDescription:String,
    date:String,
    start:String,
    duration:Float,
    fixtures:js.Array[RefObservable[Fixture]],
    fixture: Observable[js.Array[Fixture]] = Observable.of(js.Array()),
    subsidiary:Boolean = false) = new Fixtures(id,description,parentDescription,date,start,duration,fixtures, fixture, subsidiary)
}
    

class Fixture(
  val id:String,
  val description:String,
  val parentDescription:String,
  val venue: RefObservable[Venue],
  val home:RefObservable[Team],
  val away:RefObservable[Team],
  val date: String,
  val time: String,
  val duration : Float,
  val result: Result,
  val parent: Observable[Fixtures],
  val subsidiary:Boolean
) extends Model

object Fixture{
  def apply(  id:String,
  description:String,
  parentDescription:String,
  venue: RefObservable[Venue],
  home:RefObservable[Team],
  away:RefObservable[Team],
  date: String,
  time: String,
  duration : Float,
  result:Result,
  parent:Observable[Fixtures] = Observable.empty,
  subsidiary:Boolean = false) = new Fixture(id,description,parentDescription, venue, home, away,date,time,duration, result, parent, subsidiary)
  
  def addBlankResult(f:Fixture) = {
    Fixture(f.id,f.description,f.parentDescription, f.venue, f.home, f.away, f.date, f.time, f.duration, 
        Result(null.asInstanceOf[Integer],null.asInstanceOf[Integer],null,null, Observable.just(js.Array()),null),f.parent, f.subsidiary)
  }
}

class Result(
    val homeScore:Int,
    val awayScore:Int,
    val submitter:RefObservable[User],
    val note:String,
    val report:Observable[js.Array[Report]],
    val reports:RefObservable[Reports]) extends js.Object
    
object Result{
  def apply(    homeScore:Int,
    awayScore:Int,
    submitter:RefObservable[User],
    note:String,
    report:Observable[js.Array[Report]],
    reports:RefObservable[Reports]) = new Result(homeScore, awayScore, submitter, note, report, reports)
}
    

class Reports(
               val id:String,
               val reports:js.Array[Report],
               val chat:Observable[Chat],
               val isEmpty:Boolean) extends Model
    
object Reports{
  def apply(id:String,
    reports:js.Array[Report],
    chat:Observable[Chat],
    isEmpty:Boolean) = new Reports(id, reports, chat, isEmpty)
}
    

class Report(
    val team:RefObservable[Team],
    val text:RefObservable[Text]) extends Model{
    val id = UUID.randomUUID().toString()
}
    
object Report{
  def apply(team:RefObservable[Team],
    text:RefObservable[Text]) = new Report(team,text) 
}
    