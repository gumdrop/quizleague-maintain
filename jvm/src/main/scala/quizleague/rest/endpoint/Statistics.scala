package quizleague.rest.endpoint

import quizleague.domain._
import quizleague.domain.stats._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.data.Storage._
import java.util.logging.Logger
import quizleague.conversions.RefConversions._
import javax.servlet.http._
import quizleague.domain.LeagueCompetition
import quizleague.domain.util.LeagueTableRecalculator


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

  def leagueComp(season:Season) = season.competitions.map(toComp _).filter((c:Competition) => c match{
    case a:LeagueCompetition => true
    case _  => false
  }).head.asInstanceOf[LeagueCompetition]
  
  def toComp(c:Ref[Competition]):Competition = c
  
  def perform(fixture: Fixture, season: Season) = new StatsWorker(fixture, season, leagueComp(season)).doIt
}

class StatsWorker(fixture: Fixture, season: Season, tables: List[LeagueTable]) {

  implicit val context = StorageContext()
  
  val LOG: Logger = Logger.getLogger(this.getClass.getName)

  def doIt = {

    LOG.warning(s"Building stats for  ${fixture.home.shortName} vs ${fixture.away.shortName} on ${fixture.date}")
    val homeStats = stats(fixture.home, season).addWeekStats(fixture.date, fixture.result.get.homeScore, fixture.result.get.awayScore)
    val awayStats = stats(fixture.away, season).addWeekStats(fixture.date, fixture.result.get.awayScore, fixture.result.get.homeScore)

    save(homeStats)
    save(awayStats)

    for (t <- tables; row <- t.rows) {
      val s = stats(row.team, season).addLeaguePosition(fixture.date, leaguePosition(row.team, tables))
      save(s)
    }

  }

  private def stats(team: Team, season: Season): Statistics = {

    Statistics.get(team, season)

  }

  private def leaguePosition(team: Team, tables: List[LeagueTable]): Int = {

    val res = for {
      l <- tables;
      row <- l.rows if row.team.id == team.id
      pos = String.valueOf(row.position).replace("=", "").toIntOpt.getOrElse(l.rows.indexOf(row) + 1)
    } yield {
      pos
    }

    res.head
  }
}

object HistoricalStatsAggregator {

  def perform(season: Season) = {

    val seasonStats = list[Statistics].filter(_.season.id == season.id)

    for (s <- seasonStats) {

      delete(s)

    }

    val c = StatsWorker.leagueComp(season)
    var dummyTables:List[LeagueTable] = c.tables.map(t => refToObj(t).copy(rows = t.rows.map(r => r.copy(team = r.team))))

    
    
    for {
      f <- c.fixtures.sortBy(f => f.date.toString);
      r <- f.fixtures
 
    } {
      dummyTables = LeagueTableRecalculator.recalculate(dummyTables,List(r))

      new StatsWorker(r, season, dummyTables).doIt
    }
    list[Statistics]
  }

}