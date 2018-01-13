package quizleague.web.site.leaguetable

import quizleague.web.service.leaguetable.LeagueTableGetService
import quizleague.web.site.team.TeamService
import quizleague.web.core._
import quizleague.web.site.season.SeasonService
import rxscalajs.Observable._
import quizleague.web.model.CompetitionType
import scalajs.js
import js.JSConverters._

object LeagueTableModule extends Module{
  override val components = @@(LeagueTableComponent,LeagueTableRowComponent)
}


object LeagueTableService extends LeagueTableGetService {
    override val teamService = TeamService
    
    def leagueTables(seasonId:String) = {
      
     SeasonService.get(seasonId)
        .map(_.competitions
            .map(_.obs).toSeq)
            .map(cs => combineLatest(cs))
            .flatten
            .map(_.filter(_.typeName == CompetitionType.league.toString())
                .flatMap(_.tables).toJSArray
                )
    }

}

