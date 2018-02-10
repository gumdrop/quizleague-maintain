package quizleague.web.model

import quizleague.web.util.rx.RefObservable
import scala.scalajs.js

class LeagueTable(
  val id: String,
  val description: String,
  val rows: js.Array[LeagueTableRow]
) extends Model

object LeagueTable{
  def apply( 
   id: String,
   description: String,
   rows: js.Array[LeagueTableRow]) = new LeagueTable(id, description, rows)
}



class LeagueTableRow(
  val team: RefObservable[Team],
  val position: String,
  val played: Int,
  val won: Int,
  val lost: Int,
  val drawn: Int,
  val leaguePoints: Int,
  val matchPointsFor: Int,
  val matchPointsAgainst: Int
) extends js.Object

object LeagueTableRow{
  def apply(
   team: RefObservable[Team],
   position: String,
   played: Int,
   won: Int,
   lost: Int,
   drawn: Int,
   leaguePoints: Int,
   matchPointsFor: Int,
   matchPointsAgainst: Int) = new LeagueTableRow(team, position,played,won,lost,drawn, leaguePoints, matchPointsFor, matchPointsAgainst)
}