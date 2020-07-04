package quizleague.web.model

import scala.scalajs.js
import quizleague.web.util.rx.RefObservable
import quizleague.web.util.UUID
import rxscalajs.Observable
import quizleague.web.util.Logging._


class Fixtures(
    val id:String, 
    val description:String,
    val date:String,
    val start:String,
    val fixture: Observable[js.Array[Fixture]],
    val parent: Observable[Competition]) extends Model
    
object Fixtures{
  def apply(id:String, 
    description:String,
    date:String,
    start:String,
    fixture: Observable[js.Array[Fixture]] = Observable.of(js.Array()),
    parent: Observable[Competition]) = new Fixtures(id,description,date,start, fixture, parent)
}
    

class Fixture(
  val id:String,
  val venue: RefObservable[Venue],
  val home:RefObservable[Team],
  val away:RefObservable[Team],
  val result: Result,
  val parent: Observable[Fixtures]
) extends Model

object Fixture{
  def apply(  id:String,
  venue: RefObservable[Venue],
  home:RefObservable[Team],
  away:RefObservable[Team],
  result:Result,
  parent:Observable[Fixtures] = Observable.empty) = new Fixture(id, venue, home, away, result, parent)
  
  def addBlankResult(f:Fixture) = {
    val retval = Fixture(f.id, f.venue, f.home, f.away,
        Result(null.asInstanceOf[Integer],null.asInstanceOf[Integer],null,null, Observable.just(js.Array())),f.parent)
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
