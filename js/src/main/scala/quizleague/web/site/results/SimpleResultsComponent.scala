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
        <td *ngIf="inlineDetails" class="inline-details" >{{(result.fixture | async)?.date | date : "d MMM yyyy"}} : {{(result.fixture | async)?.parentDescription}} {{(result.fixture | async)?.description}}</td>
        <td *ngIf="result.fixture | async as fixture"[ngClass]="nameClass(result.homeScore, result.awayScore)">{{(fixture.home | async)?.name}}</td>
        <td>{{result.homeScore}}</td><td> - </td><td>{{result.awayScore}}</td>
        <td *ngIf="result.fixture | async as fixture" [ngClass]="nameClass(result.awayScore, result.homeScore)">{{(fixture.away | async)?.name}}</td>
        <td *ngIf="hasReports(result)">
          <a md-icon-button routerLink="/results/{{result.id}}/reports">
            <md-icon class="md-12">description</md-icon>
          </a>
        </td> 
      </tr>
    </table>      
  """,
  styles = js.Array("""
    .winner {
      	color:darkred;
        font-weight:600;
    }
  """,
  """
    .inline-details{
      font-style: italic;
      padding-right: .5em;
      color: darkblue;
    }
""")
)
class SimpleResultsComponent{  
  
  @Input("results")
  var results:js.Array[Result] = _
  
  @Input
  var inlineDetails = false
  
  def nameClass(score1:Int, score2:Int) = if(score1 > score2) "winner" else "" 
    
  def hasReports(result:Result) = !result.reports.isEmpty
    
}