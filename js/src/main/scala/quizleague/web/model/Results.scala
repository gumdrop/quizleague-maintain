package quizleague.web.model

import scalajs.js.Array

case class Results(
    id:String, 
    fixtures:Fixtures,
    results:Array[Result])
    
case class Result(
    id:String, 
    fixture:Fixture,
    homeScore:Int,
    awayScore:Int,
    submitter:User,
    note:String,
    hasReports:Boolean,
    reports:Array[Report])
    
case class Report(
    team:Team,
    text:Text)
    