package quizleague.web.site.results

import scala.scalajs.js

import angulate2.core.Input
import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std.Component
import quizleague.web.model.Result
import quizleague.web.site.common.{ MenuComponent, SectionComponent, SideMenuService }
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.season.SeasonService

@Component(
  selector = "ql-simple-results",
  template = s"""
    <table>
      <tr *ngFor="let result of results">
        <td>{{result.fixture.home.name}}</td> <td>{{result.homeScore}}</td><td> - </td><td>{{result.awayScore}}</td><td>{{result.fixture.away.name}}</td> 
      </tr>
    </table>      
  """    
)
class SimpleResultsComponent{  
  
  @Input
  var results:js.Array[Result] = _
    
}