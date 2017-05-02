package quizleague.web.site.leaguetable

import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.std.Injectable
import quizleague.web.service.leaguetable.LeagueTableGetService
import quizleague.web.site.ServiceRoot
import quizleague.web.site.team.TeamService

@Injectable
@classModeScala
class LeagueTableService(override val http: Http,
    override val teamService: TeamService
    ) extends LeagueTableGetService with ServiceRoot {

}

