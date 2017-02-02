package org.chilternquizleague.maintain.model

import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.Any.fromBoolean
import scala.scalajs.js.Any.fromString
import scala.scalajs.js.annotation.JSExportAll
import angulate2.std.Data

sealed trait Competition {
  val id:String
  val name:String
}

@JSExportAll
class LeagueCompetition(
      override val id:String,
      override val name:String,
      val startTime:Date,
      val duration:Long,
      val fixtures:js.Array[Fixtures],
      val results:js.Array[Results],
      val tables:js.Array[LeagueTable],
      val text:Text,
      val subsidiary:Competition) extends Competition

object LeagueCompetition{
  def apply(id:String,
      name:String,
      startTime:Date,
      duration:Long,
      fixtures:js.Array[Fixtures],
      results:js.Array[Results],
      tables:js.Array[LeagueTable],
      text:Text,
      subsidiary:Competition) = new LeagueCompetition(
          id,
          name,
          startTime,
          duration,
          fixtures,
          results,
          tables,
          text,
          subsidiary
          )
}


@JSExportAll
trait CupCompetition extends Competition{
  val startTime:Date
  val duration:Long
  val fixtures:js.Array[Fixtures]
  val results:js.Array[Results]
  val text:Text
}

@JSExportAll
trait SubsidiaryLeagueCompetition extends Competition{
  val startTime:Date
  val results:js.Array[Results]
  val tables:js.Array[LeagueTable]
  val text:Text
}

@Data
case class LeagueTable(id:String,retired:Boolean=false)