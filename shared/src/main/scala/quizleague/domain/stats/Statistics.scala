package quizleague.domain.stats

import quizleague.domain._
import org.threeten.bp.LocalDate

case class Statistics(
  id: String,
  team: Ref[Team],
  season: Ref[Season],
  table:Ref[LeagueTable],
  seasonStats: SeasonStats = SeasonStats(),
  weekStats: Map[String, WeekStats] = Map(),
  retired: Boolean = false) extends Entity {

  private def updateFromCurrent(stats: WeekStats, pointsFor: Int = 0, pointsAgainst: Int = 0) = {

    val week = stats.copy(
      cumuPointsFor = seasonStats.runningPointsFor + pointsFor,
      cumuPointsAgainst = seasonStats.runningPointsAgainst + pointsAgainst,
      cumuPointsDifference = seasonStats.runningPointsDifference + stats.pointsDifference)

    val season = seasonStats.copy(
      runningPointsFor = week.cumuPointsFor,
      runningPointsAgainst = week.cumuPointsAgainst,
      runningPointsDifference = week.cumuPointsDifference)

    copy(seasonStats = season, weekStats = weekStats + ((week.date.toString(), week)))
  }
  def addWeekStats(date: LocalDate, pointsFor: Int, pointsAgainst: Int) = {

    val newStats = WeekStats(date, pointsFor = pointsFor, pointsAgainst = pointsAgainst, pointsDifference = pointsFor - pointsAgainst)

    updateFromCurrent(newStats, pointsFor, pointsAgainst)

  }
  def statsForDate(date: LocalDate, alwaysNew:Boolean = true) = {
    
    if(alwaysNew) WeekStats(date) else weekStats.getOrElse(date.toString(), WeekStats(date, ignorable = true))

  }

  def addLeaguePosition(date: LocalDate, leaguePosition: Int) = {
    val stats = statsForDate(date, false)

    copy(seasonStats = seasonStats.copy(currentLeaguePosition = leaguePosition), weekStats = weekStats + ((stats.date.toString, stats.copy(leaguePosition = leaguePosition))))
  }

}

case class SeasonStats(
  currentLeaguePosition: Int = 0,
  runningPointsFor: Int = 0,
  runningPointsAgainst: Int = 0,
  runningPointsDifference: Int = 0)

case class WeekStats(
  date: LocalDate,
  leaguePosition: Int = 0,
  pointsFor: Int = 0,
  pointsAgainst: Int = 0,
  pointsDifference: Int = 0,
  cumuPointsFor: Int = 0,
  cumuPointsAgainst: Int = 0,
  cumuPointsDifference: Int = 0,
  ignorable: Boolean = false)




