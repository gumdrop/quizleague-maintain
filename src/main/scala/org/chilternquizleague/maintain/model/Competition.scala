package org.chilternquizleague.maintain.model

import scalajs.js
import angulate2.std.Data
import scala.scalajs.js.annotation.JSExport

trait Competition {
  val id:String
  val name:String
}

@JSExport
trait LeagueCompetition extends Competition{
  val startTime:String
  val duration:Int
  val fixtures:js.Array[Fixtures]
  val results:js.Array[Results]
  val tables:js.Array[LeagueTable]
  val text:Text
  val subsidiary:Competition
  
} 

@JSExport
trait CupCompetition extends Competition{
  val startTime:String
  val duration:Int
  val fixtures:js.Array[Fixtures]
  val results:js.Array[Results]
  val text:Text
}

@JSExport
trait SubsidiaryLeagueCompetition extends Competition{
  val startTime:String
  val results:js.Array[Results]
  val tables:js.Array[LeagueTable]
  val text:Text
}

@Data
case class LeagueTable(id:String,retired:Boolean=false)