package quizleague.web.site.results

import scala.scalajs.js

import angulate2.core.Input
import angulate2.std.{ Component, Data }
import quizleague.web.model.Result

@Component(
  selector = "ql-results-simple",
  template = s"""
    <table>
      <tr *ngFor="let result of results">
        <td [ngClass]="nameClass(result.homeScore, result.awayScore)">{{result.fixture.home.name}}</td> <td>{{result.homeScore}}</td><td> - </td><td>{{result.awayScore}}</td><td [ngClass]="nameClass(result.awayScore, result.homeScore)">{{result.fixture.away.name}}</td> 
      </tr>
    </table>      
  """,
  styles = js.Array("""
    .winner {
      	color:darkred;
        font-weight:600;
    }
  """)
)
class SimpleResultsComponent{  
  
  @Input("results")
  var results:js.Array[Result] = _
  
  def nameClass(score1:Int, score2:Int) = if(score1 > score2) "winner" else "" 
    
}