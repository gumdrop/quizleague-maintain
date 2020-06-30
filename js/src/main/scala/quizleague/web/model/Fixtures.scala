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
    val fixture: Observable[js.Array[Fixture]],
    val subsidiary:Boolean) extends Model
    
object Fixtures{
  def apply(id:String, 
    description:String,
    parentDescription:String,
    date:String,
    start:String,
    duration:Float,
    fixture: Observable[js.Array[Fixture]] = Observable.of(js.Array()),
    subsidiary:Boolean = false) = new Fixtures(id,description,parentDescription,date,start,duration, fixture, subsidiary)
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
    val retval = Fixture(f.id,f.description,f.parentDescription, f.venue, f.home, f.away, f.date, f.time, f.duration,
        Result(null.asInstanceOf[Integer],null.asInstanceOf[Integer],null,null, Observable.just(js.Array())),f.parent, f.subsidiary)
    retval.key = f.key
    retval
  }
}

class Result(
    val homeScore:Int,
    val awayScore:Int,
    val submitter:RefObservable[User],
    val note:String,
    val report:Observable[js.Array[Report]]) extends js.Object
    
object Result{
  def apply(    homeScore:Int,
    awayScore:Int,
    submitter:RefObservable[User],
    note:String,
    report:Observable[js.Array[Report]]) = new Result(homeScore,awayScore,submitter,note,report)
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
