package quizleague.web.site.leaguetable


import quizleague.web.model.LeagueTable
import scalajs.js
import js.JSConverters._
import js.DynamicImplicits

import quizleague.web.util.rx.RefObservable
import quizleague.web.core._
import quizleague.web.model._
import KeyComponent._


object LeagueTableComponent extends Component{
    
  type facade = KeyComponent
  
  val name = "ql-league-table"
  val template = """
    <v-slide-y-transition>
      <table v-if="table" class="mat-elevation-z3 ql-league-table elevation-3">
        <caption>{{table.description}}</caption>
        <thead>
          <th>Pos.</th><th>Team</th><th>Pl.</th><th>W</th><th>D</th><th>L</th><th>S</th><th>Pts</th>
        </thead>
        <tbody>
          <ql-league-table-row :row="row" v-for="row in table.rows" :key="row.team.id"></ql-league-table-row>
        </tbody>
      </table>
    </v-slide-y-transition>
"""
  props("keyval")
  subscription("table","keyval")(c => LeagueTableService.get(key(c)))
  method("sort")((rows:js.Array[LeagueTableRow]) => rows.sortBy(_.position.toInt))
  
  
}

object LeagueTableRowComponent extends Component{
  
  val name = "ql-league-table-row"
  val template = """
        <tr >
            <td>{{row.position}}</td><td><router-link :to="'/team/' + row.team.id"><ql-r-team-name :id="row.team.id"></ql-r-team-name></router-link></a></td><td class="num">{{row.played}}</td><td class="num">{{row.won}}</td><td class="num">{{row.drawn}}</td><td class="num">{{row.lost}}</td><td class="num">{{row.matchPointsFor}}</td><td class="num">{{row.leaguePoints}}</td>
          </tr>"""
  
  props("row")
  
}
