package quizleague.web.model

import scalajs.js.Array
import angulate2.std.Data
import quizleague.web.util.rx.RefObservable

@Data
case class Results(
    id:String, 
    fixtures:RefObservable[Fixtures],
    results:Array[RefObservable[Result]])
    
@Data
case class Result(
    id:String, 
    fixture:RefObservable[Fixture],
    homeScore:Int,
    awayScore:Int,
    submitter:RefObservable[User],
    note:String,
    hasReports:Boolean,
    reports:Array[Report])
    
@Data
case class Report(
    team:RefObservable[Team],
    text:RefObservable[Text])
    