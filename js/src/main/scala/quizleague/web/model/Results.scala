package quizleague.web.model

import scalajs.js.Array
import angulate2.std.Data

@Data
case class Results(
    id:String, 
    fixtures:Fixtures,
    results:Array[Result])
    
@Data
case class Result(
    id:String, 
    fixture:Fixture,
    homeScore:Int,
    awayScore:Int,
    submitter:User,
    note:String,
    hasReports:Boolean,
    reports:Array[Report])
    
@Data
case class Report(
    team:Team,
    text:Text)
    