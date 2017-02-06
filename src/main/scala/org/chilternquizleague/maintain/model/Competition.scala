package org.chilternquizleague.maintain.model

import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.Any.fromBoolean
import scala.scalajs.js.Any.fromString
import scala.scalajs.js.annotation.JSExportAll
import angulate2.std.Data

object CompetitionType extends Enumeration {
  type CompetitionType = Value
  val league, cup, subsidiary = Value
}

import CompetitionType._
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
sealed trait Competition extends js.Object {
  val id:String
  val name:String
  val typeName:String
}

@ScalaJSDefined
class LeagueCompetition (
      override val id:String,
      override val name:String,
      val startTime:Date,
      val duration:Long,
      val fixtures:js.Array[Fixtures],
      val results:js.Array[Results],
      val tables:js.Array[LeagueTable],
      val text:Text,
      val subsidiary:Competition) extends Competition{
  override val typeName = league.toString()
}




@ScalaJSDefined
class CupCompetition(
      override val id:String,
      override val name:String,
      val startTime:Date,
      val duration:Long,
      val fixtures:js.Array[Fixtures],
      val results:js.Array[Results],
      val text:Text) extends Competition{
  override val typeName = cup.toString()
}

@ScalaJSDefined
class SubsidiaryLeagueCompetition(     
      override val id:String,
      override val name:String,
      val results:js.Array[Results],
      val tables:js.Array[LeagueTable],
      val text:Text) extends Competition{
  override val typeName = subsidiary.toString()
}

@Data
case class LeagueTable(id:String,retired:Boolean=false)