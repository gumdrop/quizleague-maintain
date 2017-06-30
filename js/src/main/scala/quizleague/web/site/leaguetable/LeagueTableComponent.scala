package quizleague.web.site.leaguetable

import angulate2.std._
import quizleague.web.model.LeagueTable
import rxjs.Observable

@Component(
    selector = "ql-league-table",
    template = """
      <table *ngIf="itemObs | async as item; else loading" >
        <caption>{{item.description}}</caption>
        <thead>
          <th>Pos.</th><th>Team</th><th>Pl.</th><th>W</th><th>D</th><th>L</th><th>S</th><th>Pts</th>
        </thead>
        <tbody>
          <tr *ngFor="let row of item.rows">
            <td>{{row.position}}</td><td><a routerLink="/team/{{(row.team | async)?.id}}">{{(row.team | async)?.shortName}}</a></td><td class="num">{{row.played}}</td><td class="num">{{row.won}}</td><td class="num">{{row.lost}}</td><td class="num">{{row.drawn}}</td><td class="num">{{row.matchPointsFor}}</td><td class="num">{{row.leaguePoints}}</td>
          </tr>
        </tbody>
      </table>
      <ng-template #loading>Loading...</ng-template>
    """,
    styles = @@@("""
      table {
        border : 1.5px solid black;
        font-size:14px;
      }
      caption{
        text-align:left;
        font-size: 16px;
        font-weight: 500;
        font-family: Roboto,"Helvetica Neue",sans-serif;
      }""",
      """
      td{
        border-bottom: 0.5px solid rgba(0,0,0,.5);
        border-right: 0.5px solid rgba(0,0,0,.5);
      }
      th{
        border-bottom: 0.5px solid rgba(0,0,0,1);
        border-right: 0.5px solid rgba(0,0,0,1);
      }
      """,
      """
      .num{
        min-width:3em;
        text-align:right;
      }
      """
    )
)
class LeagueTableComponent(
  service:LeagueTableService 
) extends OnInit{
  
  @Input
  var table:LeagueTable = _
  
  var itemObs:Observable[LeagueTable] = _
  
  override def ngOnInit() = {
    itemObs = service.get(table.id)
  }
  
}