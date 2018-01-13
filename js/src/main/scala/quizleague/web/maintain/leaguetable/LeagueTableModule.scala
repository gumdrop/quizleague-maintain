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
import quizleague.web.maintain.MaintainMenuComponent

object LeagueTableModule extends Module{
    override val routes = @@(     
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:competitionId/leaguetable/:id",
        components = Map("default" -> LeagueTableComponent, "sidenav" -> MaintainMenuComponent)
      ),
      
  )
  
}

object LeagueTableService extends LeagueTableGetService with LeagueTablePutService with PostService{
  
  override val teamService = TeamService
  
    
  def recalculateTable(table:LeagueTable, competition:Competition) = {
//    val res = command[Dom,String](List(table.id,"competition",competition.id, "recalc"),None)
//    res.map((u,i) => mapOutSparse(u))
  }
}