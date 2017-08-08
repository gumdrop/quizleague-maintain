package quizleague.web.site.leaguetable

import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.std._
import quizleague.web.service.leaguetable.LeagueTableGetService
import quizleague.web.site.ServiceRoot
import quizleague.web.site.team.TeamService
import angulate2.common.CommonModule
import angulate2.router.RouterModule
import angular.material.MaterialModule


@NgModule(
  imports = @@[CommonModule, RouterModule, MaterialModule],
  declarations = @@[LeagueTableComponent],
  providers = @@[LeagueTableService],
  exports = @@[LeagueTableComponent]
  )
class LeagueTableModule



@Injectable
@classModeScala
class LeagueTableService(override val http: Http,
    override val teamService: TeamService
    ) extends LeagueTableGetService with ServiceRoot {

}

