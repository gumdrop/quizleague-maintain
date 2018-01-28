package quizleague.rest.endpoint

import quizleague.domain._
import quizleague.domain.statistics._
import quizleague.data.Storage._
import java.util.logging.Logger
import quizleague.conversions.RefConversions._
import javax.servlet.http._
import quizleague.domain.LeagueCompetition

//class StatsQueueHandler extends HttpServlet {
//  val LOG: Logger = Logger.getLogger(this.getClass.getName)
//  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
//
//    LOG.fine(s"task arrived : ${req.getParameter("result")}")
//
//    for {
//      resText <- req.parameter("result")
//      r = JacksonUtils.safeMapper.readValue(resText, classOf[Result])
//      season <- entity[Season](req.id("seasonId"))
//    } {
//      StatsWorker.perform(r, season)
//    }
//  }
//}

object StatsWorker {

  def toComp(c:Ref[Competition]):Competition = c
  
  def perform(fixture: Fixture, season: Season) = new StatsWorker(fixture, season, season.competitions.map(toComp _).filter((c:Competition) => c match{
    case a:LeagueCompetition => true
    case _  => false
  }).head.asInstanceOf[LeagueCompetition]).doIt
}

class StatsWorker(fixture: Fixture, season: Season, competition: LeagueCompetition) {

  implicit val context = StorageContext()
  
  val LOG: Logger = Logger.getLogger(this.getClass.getName)

  def doIt = {

    LOG.warning(s"Building stats for  ${fixture.home.shortName} vs ${fixture.away.shortName} on ${fixture.date}")
    val homeStats = stats(fixture.home, season)
    val awayStats = stats(fixture.away, season)

    homeStats.addWeekStats(fixture.date, fixture.result.get.homeScore, fixture.result.awayScore)
    save(homeStats)
    awayStats.addWeekStats(fixture.date, fixture.result.get.awayScore, fixture.result.homeScore)
    save(awayStats)

    for (t <- competition.leagueTables; row <- t.rows) {
      val s = stats(row.team, season)
      s.addLeaguePosition(result.fixture.start, leaguePosition(row.team, competition))
      save(s)
    }

  }

  private def stats(team: Team, season: Season): Statistics = {

    Statistics.get(team, season)

  }

  private def leaguePosition(team: Team, competition: LeagueCompetition): Int = {
    import org.chilternquizleague.util.StringUtils.StringImprovements

    val res = for {
      l <- competition.leagueTables
      row <- l.rows if row.team.getKey.getId == team.id
      pos = String.valueOf(row.position).replace("=", "").toIntOpt.getOrElse(l.rows.indexOf(row) + 1)
    } yield {
      pos
    }

    res.head
  }
}

object HistoricalStatsAggregator {

  def perform(season: Season) = {

    val seasonStats = new ArrayList(entityList(classOf[Statistics]).filter(_.season.id == season.id))

    for (s <- seasonStats) {

      transaction(() => delete(s))

    }

    val c: LeagueCompetition = season.competition(CompetitionType.LEAGUE)
    val dummyComp = c.copyAsInitial

    for {
      r <- c.results.sortBy(_.date)
      result <- r.results
    } {
      dummyComp.addResult(result)

      new StatsWorker(result, season, dummyComp).doIt
    }
    entityList(classOf[Statistics])
  }

}