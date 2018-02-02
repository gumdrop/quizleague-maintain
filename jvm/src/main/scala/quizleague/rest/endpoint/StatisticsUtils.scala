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
import quizleague.util.StringUtils._
import java.util.UUID.{randomUUID => uuid}


object StatsWorker {

  def leagueComp(season:Season) = season.competitions.map(toComp _).flatMap((c:Competition) => c match{
    case a:LeagueCompetition => List(a)
    case _  => List()
  }).head
  
  def toComp(c:Ref[Competition]):Competition = c
  
  def perform(fixture: Fixture, season: Season){
    val stats = new StatsWorker(fixture, season, leagueComp(season).tables).doIt
    saveAll(stats)
  }
}

class StatsWorker(fixture: Fixture, season: Season, tables: List[LeagueTable]) {

  implicit val context = StorageContext()
  
  val LOG: Logger = Logger.getLogger(this.getClass.getName)

  def doIt = {

    LOG.warning(s"Building stats for  ${fixture.home.shortName} vs ${fixture.away.shortName} on ${fixture.date}")
    val homeStats = find(fixture.home, season).addWeekStats(fixture.date, fixture.result.get.homeScore, fixture.result.get.awayScore)
    val awayStats = find(fixture.away, season).addWeekStats(fixture.date, fixture.result.get.awayScore, fixture.result.get.homeScore)

//    save(homeStats)
//    save(awayStats)

    homeStats :: awayStats :: (for (t <- tables; row <- t.rows) yield find(row.team, season).addLeaguePosition(fixture.date, leaguePosition(row.team, tables)))

  }

  private def find(team: Team, season: Season): Statistics = {

    def comp = StatsWorker.leagueComp(season)
    def table = comp.tables.filter(t => t.rows.exists(_.team.id == team.id)).head
    
    
    list[Statistics].filter(s => s.season.id == season.id && s.team.id == team.id)
    .headOption
    .getOrElse(Statistics(uuid.toString,Ref[Team]("team", team.id), Ref[Season]("season", season.id), table))

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

    deleteAll(seasonStats)

    val c = StatsWorker.leagueComp(season)
    var dummyTables:List[LeagueTable] = c.tables.map(t => refToObject(t).copy(rows = t.rows.map(r => r.copy(team = r.team))))

    
    
    val stats = for (
      f <- c.fixtures.sortBy(f => f.date.toString);
      r <- f.fixtures
 
  ) yield {
      dummyTables = LeagueTableRecalculator.recalculate(dummyTables,List(r))

      new StatsWorker(r, season, dummyTables).doIt
    }
    saveAll(stats.flatten)
  }

}