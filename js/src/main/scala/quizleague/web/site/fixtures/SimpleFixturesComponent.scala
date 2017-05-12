package quizleague.web.site.fixtures

import scala.scalajs.js

import angulate2.core.Input
import angulate2.std._
import quizleague.web.model.Fixture

@Component(
  selector = "ql-fixtures-simple",
  template = s"""
    <table>
      <tr *ngFor="let fixture of fixtures">
        <td *ngIf="inlineDetails" class="inline-details" >{{fixture.date | date : "d MMM yyyy"}} : {{fixture.parentDescription}} {{fixture.description}}</td><td>{{fixture.home.name}}</td><td> - </td><td>{{fixture.away.name}}</td> 
      </tr>
    </table>      
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
  var fixtures:js.Array[Fixture] = _
  
  @Input
  var inlineDetails = false  
  
}