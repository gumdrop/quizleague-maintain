package quizleague.web.model

import scala.scalajs.js
import quizleague.web.util.rx.RefObservable
import quizleague.web.util.UUID


class Fixtures(
    val id:String, 
    val description:String,
    val parentDescription:String,
    val date:String,
    val start:String,
    val duration:Float,
    val fixtures:js.Array[RefObservable[Fixture]],
    val subsidiary:Boolean) extends Model
    
object Fixtures{
  def apply(id:String, 
    description:String,
    parentDescription:String,
    date:String,
    start:String,
    duration:Float,
    fixtures:js.Array[RefObservable[Fixture]],
    subsidiary:Boolean = false) = new Fixtures(id,description,parentDescription,date,start,duration,fixtures, subsidiary)
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
  subsidiary:Boolean = false) = new Fixture(id,description,parentDescription, venue, home, away,date,time,duration, result, subsidiary)
  
  def addBlankResult(f:Fixture) = {
    Fixture(f.id,f.description,f.parentDescription, f.venue, f.home, f.away, f.date, f.time, f.duration, 
        Result(null.asInstanceOf[Integer],null.asInstanceOf[Integer],null,null,null),f.subsidiary)
  }
}

class Result(
    val homeScore:Int,
    val awayScore:Int,
    val submitter:RefObservable[User],
    val note:String,
    val reports:RefObservable[Reports]) extends js.Object
    
object Result{
  def apply(    homeScore:Int,
    awayScore:Int,
    submitter:RefObservable[User],
    note:String,
    reports:RefObservable[Reports]) = new Result(homeScore, awayScore, submitter, note, reports) 
}
    

class Reports(
    val id:String,
    val reports:js.Array[Report],
    val isEmpty:Boolean) extends Model with Child
    
object Reports{
  def apply(id:String,
    reports:js.Array[Report],
    isEmpty:Boolean) = new Reports(id, reports, isEmpty)
}
    

class Report(
    val team:RefObservable[Team],
    val text:RefObservable[Text]) extends js.Object{
    val id = UUID.randomUUID().toString()
}
    
object Report{
  def apply(    team:RefObservable[Team],
    text:RefObservable[Text]) = new Report(team,text) 
}
    