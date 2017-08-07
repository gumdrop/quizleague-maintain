package quizleague.web.site.results

import scala.scalajs.js

import angulate2.core.Input
import angulate2.std.{ Component, Data }
import quizleague.web.model.Result
import rxjs.Observable
import quizleague.web.util.rx._
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._


@Component(
  selector = "ql-results-simple",
  template = s"""
    <table *ngIf="results | async as rs; else loading">
      <ng-template ngFor let-result [ngForOf]="rs">
        <tr *ngIf="result.fixture | async as fixture">
          <td *ngIf="inlineDetails" class="inline-details" >{{fixture.date | date : "d MMM yyyy"}} : {{fixture.parentDescription}} {{fixture.description}}</td>
          <td [ngClass]="nameClass(result.homeScore, result.awayScore)">{{(fixture.home | async)?.name}}</td>
          <td>{{result.homeScore}}</td><td> - </td><td>{{result.awayScore}}</td>
          <td [ngClass]="nameClass(result.awayScore, result.homeScore)">{{(fixture.away | async)?.name}}</td>
          <td *ngIf="hasReports(result)">
            <a md-icon-button routerLink="/results/{{result.id}}/reports">
              <md-icon class="md-12">description</md-icon>
            </a>
          </td> 
        </tr>
      </ng-template>
    </table>    
    $loadingTemplate  
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
  var results:Observable[js.Array[Result]] = _
  
  @Input
  def list_= (list:js.Array[RefObservable[Result]]):Unit = results = zip(list)
  
  @Input
  var inlineDetails = false
  
  def nameClass(score1:Int, score2:Int) = if(score1 > score2) "winner" else if(score1 == score2) "orange" else ""
    
  def hasReports(result:Result) = !result.reports.isEmpty
    
}