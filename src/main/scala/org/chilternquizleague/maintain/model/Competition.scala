package org.chilternquizleague.maintain.model

import scala.scalajs.js
import scala.scalajs.js.Any.fromBoolean
import scala.scalajs.js.Any.fromString
import scala.scalajs.js.annotation.JSExportAll
import angulate2.std.Data

trait Competition {
  val id:String
  val name:String
}

@JSExportAll
trait LeagueCompetition extends Competition{
  val startTime:String
  val duration:Int
  val fixtures:js.Array[Fixtures]
  val results:js.Array[Results]
  val tables:js.Array[LeagueTable]
  val text:Text
  val subsidiary:Competition
  
} 

@JSExportAll
trait CupCompetition extends Competition{
  val startTime:String
  val duration:Int
  val fixtures:js.Array[Fixtures]
  val results:js.Array[Results]
  val text:Text
}

@JSExportAll
trait SubsidiaryLeagueCompetition extends Competition{
  val startTime:String
  val results:js.Array[Results]
  val tables:js.Array[LeagueTable]
  val text:Text
}

@Data
case class LeagueTable(id:String,retired:Boolean=false)