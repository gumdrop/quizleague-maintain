package quizleague.web.model

import quizleague.web.util.rx._
import java.time.LocalDate

import quizleague.web.util.UUID
import rxscalajs.Observable

import scalajs.js

class Statistics(
  val id: String,
  val team: RefObservable[Team],
  val season: RefObservable[Season],
  val table: RefObservable[LeagueTable],
  val seasonStats: SeasonStats = new SeasonStats(),
  val weekStats: js.Array[WeekStats] = js.Array())
  extends Model

object Statistics {

  def stub(season: Season) = new Statistics(
    UUID.randomUUID().toString(),
    null,
    RefObservable(season.key, () => Observable.of(season)),
    null
  )

}

class HeadToHead(
  val team:RefObservable[Team],
  val win:Int = 0,
  val lose:Int = 0,
  val draw:Int = 0) extends js.Object{

  def plus (other:HeadToHead):HeadToHead = new HeadToHead(other.team, other.win + win, other.lose + lose, other.draw + draw)
}

class SeasonStats(
  val currentLeaguePosition: Int = 0,
  val runningPointsFor: Int = 0,
  val runningPointsAgainst: Int = 0,
  val runningPointsDifference: Int = 0,
  val headToHead:js.Array[HeadToHead] = js.Array()) extends js.Object

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

