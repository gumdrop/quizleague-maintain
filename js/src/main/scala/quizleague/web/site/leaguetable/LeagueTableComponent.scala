package quizleague.web.site.leaguetable


import quizleague.web.model.LeagueTable
import scalajs.js
import js.DynamicImplicits

import quizleague.web.util.rx.RefObservable
//import quizleague.web.site.common.ComponentUtils
//import ComponentUtils._
import quizleague.web.core._

//@Component(
//    selector = "ql-league-table",
//    template = s"""
//      <table *ngIf="table | async as item; else loading" class="mat-elevation-z3">
//        <caption>{{item.description}}</caption>
//        <thead>
//          <th>Pos.</th><th>Team</th><th>Pl.</th><th>W</th><th>D</th><th>L</th><th>S</th><th>Pts</th>
//        </thead>
//        <tbody>
//          <ng-template ngFor let-row [ngForOf]="item.rows">
//          <tr *ngIf="row.team | async as team">
//            <td>{{row.position}}</td><td><a routerLink="/team/{{team.id}}">{{team.shortName}}</a></td><td class="num">{{row.played}}</td><td class="num">{{row.won}}</td><td class="num">{{row.lost}}</td><td class="num">{{row.drawn}}</td><td class="num">{{row.matchPointsFor}}</td><td class="num">{{row.leaguePoints}}</td>
//          </tr>
//          </ng-template> 
//        </tbody>
//      </table>
//    """,
//    styles = @@@("""
//      table {
//        border : 1px solid rgba(0,0,0,.25);
//        font-size:14px;
//      }
//      caption{
//        text-align:left;
//        font-size: 16px;
//        font-weight: 500;
//        font-family: Roboto,"Helvetica Neue",sans-serif;
//      }""",
//      """
//      td{
//        border-bottom: 0.5px solid rgba(0,0,0,.25);
//        border-right: 0.5px solid rgba(0,0,0,.25);
//      }
//      th{
//        border-bottom: 0.5px solid rgba(0,0,0,.25);
//        border-right: 0.5px solid rgba(0,0,0,.25);
//      }
//      """,
//      """
//      .num{
//        min-width:2em;
//        text-align:right;
//      }
//      """
//    )
//)
object LeagueTableComponent extends Component{
    
  type facade = IdComponent
  
  val name = "ql-league-table"
  val template = """
      <table v-if="table" class="mat-elevation-z3 ql-league-table elevation-3">
        <caption>{{table.description}}</caption>
        <thead>
          <th>Pos.</th><th>Team</th><th>Pl.</th><th>W</th><th>D</th><th>L</th><th>S</th><th>Pts</th>
        </thead>
        <tbody>
          <ql-league-table-row :row="row" v-for="row in table.rows" :key="row.team.id"></ql-league-table-row>
        </tbody>
      </table>
"""
  props("id")
  subscription("table","id")(c => LeagueTableService.get(c.id))
  
  
}

object LeagueTableRowComponent extends Component{
  
  val name = "ql-league-table-row"
  val template = """
        <tr >
            <td>{{row.position}}</td><td><router-link :to="'/team/' + row.team.id"><ql-team-name :team="row.team" short="true"></ql-team-name></router-link></a></td><td class="num">{{row.played}}</td><td class="num">{{row.won}}</td><td class="num">{{row.lost}}</td><td class="num">{{row.drawn}}</td><td class="num">{{row.matchPointsFor}}</td><td class="num">{{row.leaguePoints}}</td>
          </tr>"""
  
  props("row")
  
}
