package quizleague.web.site.leaguetable

import angulate2.std._
import quizleague.web.model.LeagueTable
import rxjs.Observable
import quizleague.web.util.rx.RefObservable
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._

@Component(
    selector = "ql-league-table",
    template = s"""
      <table *ngIf="table | async as item; else loading" class="mat-elevation-z3">
        <caption>{{item.description}}</caption>
        <thead>
          <th>Pos.</th><th>Team</th><th>Pl.</th><th>W</th><th>D</th><th>L</th><th>S</th><th>Pts</th>
        </thead>
        <tbody>
          <ng-template ngFor let-row [ngForOf]="item.rows">
          <tr *ngIf="row.team | async as team">
            <td>{{row.position}}</td><td><a routerLink="/team/{{team.id}}">{{team.shortName}}</a></td><td class="num">{{row.played}}</td><td class="num">{{row.won}}</td><td class="num">{{row.lost}}</td><td class="num">{{row.drawn}}</td><td class="num">{{row.matchPointsFor}}</td><td class="num">{{row.leaguePoints}}</td>
          </tr>
          </ng-template> 
        </tbody>
      </table>
      $loadingTemplate
    """,
    styles = @@@("""
      table {
        border : 1px solid rgba(0,0,0,.25);
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
        border-bottom: 0.5px solid rgba(0,0,0,.25);
        border-right: 0.5px solid rgba(0,0,0,.25);
      }
      th{
        border-bottom: 0.5px solid rgba(0,0,0,.25);
        border-right: 0.5px solid rgba(0,0,0,.25);
      }
      """,
      """
      .num{
        min-width:2em;
        text-align:right;
      }
      """
    )
)
class LeagueTableComponent(
  service:LeagueTableService 
){
  
  @Input
  var table:Observable[LeagueTable] = _
  
  @Input
  def ref_= (ref:RefObservable[LeagueTable]):Unit = table = ref.obs
  
  var itemObs:Observable[LeagueTable] = _
  
 
  
}