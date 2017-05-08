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
        <td>{{fixture.home.name}}</td><td> - </td><td>{{fixture.away.name}}</td> 
      </tr>
    </table>      
  """    
)
class SimpleFixturesComponent{  
  
  @Input
  var fixtures:js.Array[Fixture] = _
    
}