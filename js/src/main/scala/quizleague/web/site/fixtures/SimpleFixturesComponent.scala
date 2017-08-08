package quizleague.web.site.fixtures

import scala.scalajs.js

import angulate2.core.Input
import angulate2.std._
import quizleague.web.model.Fixture
import quizleague.web.util.rx._
import rxjs.Observable
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._

@Component(
  selector = "ql-fixtures-simple",
  template = s"""
    <table *ngIf="fixtures | async as fs else loading">
      <tr *ngFor="let fixture of fs">
        <td *ngIf="inlineDetails" class="inline-details" >{{fixture.date | date : "d MMM yyyy"}} : {{fixture.parentDescription}} {{fixture.description}}</td>
        <td>{{(fixture.home | async)?.shortName}}</td>
        <td> - </td>
        <td>{{(fixture.away | async)?.shortName}}</td> 
      </tr>
    </table> 
    $loadingTemplate     
  """,
    styles = js.Array(
  """
    .inline-details{
      font-style: italic;
      padding-right: .5em;
      color: darkblue;
    }
""")
)
class SimpleFixturesComponent{  
  
  @Input
  var fixtures:Observable[js.Array[Fixture]] = _
  
  @Input
  def list_= (list:js.Array[RefObservable[Fixture]]) = fixtures = zip(list)
  
  @Input
  var inlineDetails = false  
  
}