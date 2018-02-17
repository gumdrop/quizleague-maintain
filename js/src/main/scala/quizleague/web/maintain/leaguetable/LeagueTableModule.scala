package quizleague.web.maintain.leaguetable

import quizleague.web.service.leaguetable.LeagueTableGetService
import quizleague.web.service.leaguetable.LeagueTablePutService
import quizleague.web.maintain.team.TeamService
import quizleague.web.service.PostService
import quizleague.web.model._
import quizleague.domain.{LeagueTable => Dom}
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.web.core._
import quizleague.web.core.RouteConfig
import quizleague.util.collection._
import scalajs.js
import js.JSConverters._

object LeagueTableModule extends Module{
    override val routes = @@(     
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:competitionId/leaguetable/:id",
        components = Map("default" -> LeagueTableComponent)
      ),
      
  )
  
}

object LeagueTableService extends LeagueTableGetService with LeagueTablePutService with PostService{
  
  override val teamService = TeamService
  
    
  def recalculateTable(table:LeagueTable, competition:Competition) = {
    command[Dom,String](List("entity","recalculate-table",table.id,competition.id),None).subscribe(x => Unit)
  }
  

}