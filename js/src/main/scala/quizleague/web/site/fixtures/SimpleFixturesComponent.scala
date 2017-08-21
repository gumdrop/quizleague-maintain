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
   <div  in-viewport (inViewport)="load($$event.value)">
    &nbsp;
    <div *ngIf="inView">
    <table *ngIf="fixtures | async as fs else loading">
      <tr *ngFor="let fixture of fs">
        <td *ngIf="inlineDetails" class="inline-details" >{{fixture.date | date : "d MMM yyyy"}} : {{fixture.parentDescription}} {{fixture.description}}</td>
        <td class="home">{{(fixture.home | async)?.name}}</td>
        <td> - </td>
        <td class="away">{{(fixture.away | async)?.name}}</td> 
      </tr>
    </table>
    </div> 
    </div>
    $loadingTemplate     
  """,
    styles = js.Array(
  """
    .inline-details{
      font-style: italic;
      padding-right: .5em;
      color: darkblue;
    }
""",
""".home{
      text-align:right;
      padding-right:1em;
    }
    .away{
      padding-left:1em;
    }""")
)
class SimpleFixturesComponent{  
  
  var inView = false
  
  @Input
  var loadIfHidden = false
  
  @Input
  var fixtures:Observable[js.Array[Fixture]] = _
  
  @Input
  def list_= (list:js.Array[RefObservable[Fixture]]) = fixtures = zip(list)
  
  @Input
  var inlineDetails = false  
  
  def load(event:Boolean){
    inView = event || inView || loadIfHidden
  }
  
}