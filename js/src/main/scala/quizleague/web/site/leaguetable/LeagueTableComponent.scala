package quizleague.web.site.leaguetable

import angulate2.std._
import quizleague.web.model.LeagueTable
import rxjs.Observable

@Component(
    selector = "ql-league-table",
    template = """
      <table>
        <thead>
          <th>Pos.</th><th>Team</th><th>Pl.</th><th>W</th><th>D</th><th>L</th><th>S</th><th>Pts</th>
        </thead>
        <tbody>
          <tr *ngFor="let row of (item | async).rows">
            <td>{{row.position}}</td><td>{{row.team.shortName}}</td><td>{{row.played}}</td><td>{{row.won}}</td><td>{{row.lost}}</td><td>{{row.drawn}}</td><td>{{row.matchPointsFor}}</td><td>{{row.leaguePoints}}</td>
          </tr>
        </tbody>
      </table>

"""
)
class LeagueTableComponent(
  service:LeagueTableService 
) extends OnInit{
  
  @Input
  var table:LeagueTable = _
  
  var item:Observable[LeagueTable] = _
  
  override def ngOnInit() = {
    item = service.get(table.id)
  }
  
}