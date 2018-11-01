package quizleague.web.service.statistics

import scalajs.js
import js.JSConverters._
import quizleague.web.service._
import quizleague.web.model.Statistics
import quizleague.web.model.SeasonStats
import quizleague.web.model.WeekStats
import quizleague.web.model.HeadToHead
import quizleague.domain.stats.{ Statistics => Dom }
import shapeless._
import quizleague.web.names.StatisticsNames
import io.circe._,io.circe.parser._,io.circe.syntax._,io.circe.scalajs.convertJsToJson
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.web.service.team.TeamGetService
import quizleague.web.service.season.SeasonGetService
import quizleague.web.service.leaguetable.LeagueTableGetService


trait StatisticsGetService extends GetService[Statistics] with StatisticsNames {
  
  val teamService:TeamGetService
  val seasonService:SeasonGetService
  val tableService:LeagueTableGetService
  
  override type U = Dom
  override protected def mapOutSparse(s: Dom): Statistics = 
    new Statistics(s.id, refObs(s.team,teamService), refObs(s.season,seasonService), refObs(s.table,tableService), 
        new SeasonStats(
            s.seasonStats.currentLeaguePosition,
            s.seasonStats.runningPointsFor,
            s.seasonStats.runningPointsAgainst,
            s.seasonStats.runningPointsDifference,
          s.seasonStats.headToHead.map(h => new HeadToHead(refObs(h.team,teamService), h.win,h.lose,h.draw)).toJSArray
        ),
         s.weekStats.values.map(w => new WeekStats(
           w.date.toString,
           w.leaguePosition,
           w.pointsFor,
           w.pointsAgainst,
           w.pointsDifference,
           w.cumuPointsFor,
           w.cumuPointsAgainst,
           w.cumuPointsDifference,
           w.ignorable
         )).toJSArray.sortBy(_.date))
  protected def dec(json:js.Any) = decodeJson[U](json)
}