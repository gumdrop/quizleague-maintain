package quizleague.domain.stats

import quizleague.domain._
import java.time.LocalDate

case class Statistics(
                       id: String,
                       team: Ref[Team],
                       season: Ref[Season],
                       table: Ref[LeagueTable],
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

  def statsForDate(date: LocalDate, alwaysNew: Boolean = true) = {

    if (alwaysNew) WeekStats(date) else weekStats.getOrElse(date.toString(), WeekStats(date, ignorable = true))

  }

  def addLeaguePosition(date: LocalDate, leaguePosition: Int) = {
    val stats = statsForDate(date, false)

    copy(seasonStats = seasonStats.copy(currentLeaguePosition = leaguePosition), weekStats = weekStats + ((stats.date.toString, stats.copy(leaguePosition = leaguePosition))))
  }

  def addToHeadToHead(fixture: Fixture) = {
    if(fixture.result.isDefined){
      val r = fixture.result.get
      val otherTeam = List(fixture.home, fixture.away).filter(_.id != team.id).head

      case class WLD(win:Int,loss:Int,draw:Int)

      def getWLD() ={
        val normalisedScores = if (fixture.home.id == otherTeam.id) (r.awayScore, r.homeScore) else (r.homeScore, r.awayScore)
        val win = if (normalisedScores._1 > normalisedScores._2) 1 else 0
        val draw = if (normalisedScores._1 == normalisedScores._2) 1 else 0
        val loss = (win + draw - 1) * -1

        WLD(win,loss,draw)
      }

      val wld = getWLD()

      val headToHead = HeadToHead(otherTeam, wld.win, wld.loss, wld.draw)
      val headToHeads = (seasonStats.headToHead.map(h => (h.team.id, h)).toMap.get(otherTeam.id).getOrElse(HeadToHead(otherTeam)) + headToHead) :: seasonStats.headToHead.filter(h => h.team.id != otherTeam.id)

      val newSeasonStats = seasonStats.copy(headToHead = headToHeads)

      copy(seasonStats = newSeasonStats)

    }else this


  }

}

case class HeadToHead(
                       team: Ref[Team],
                       win: Int = 0,
                       lose: Int = 0,
                       draw: Int = 0){

  def +(other:HeadToHead) = HeadToHead(team, win + other.win, lose + other.lose, draw + other.draw)

}

case class SeasonStats(
                        currentLeaguePosition: Int = 0,
                        runningPointsFor: Int = 0,
                        runningPointsAgainst: Int = 0,
                        runningPointsDifference: Int = 0,
                        headToHead: List[HeadToHead] = List())

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




