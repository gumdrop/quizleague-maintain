package quizleague.web.model

import quizleague.web.util.rx._
import org.threeten.bp.LocalDate
import scalajs.js

class Statistics(
  val id: String,
  val team: RefObservable[Team],
  val season: RefObservable[Season],
  val table: RefObservable[LeagueTable],
  val seasonStats: SeasonStats = new SeasonStats(),
  val weekStats: js.Array[WeekStats] = js.Array())
  extends Model

class SeasonStats(
  val currentLeaguePosition: Int = 0,
  val runningPointsFor: Int = 0,
  val runningPointsAgainst: Int = 0,
  val runningPointsDifference: Int = 0) extends js.Object

class WeekStats(
  val date: String,
  val leaguePosition: Int = 0,
  val pointsFor: Int = 0,
  val pointsAgainst: Int = 0,
  val pointsDifference: Int = 0,
  val cumuPointsFor: Int = 0,
  val cumuPointsAgainst: Int = 0,
  val cumuPointsDifference: Int = 0,
  val ignorable: Boolean = false) extends js.Object