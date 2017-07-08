package quizleague.web.maintain.team

import angulate2.std._
import quizleague.web.maintain.component.TemplateElements._

import scala.scalajs.js
import js.JSConverters._
import quizleague.web.util.UUID
import angulate2.router.Router
import quizleague.web.maintain.component.ListComponent
import quizleague.web.util.UUID
import quizleague.web.model._
import angulate2.ext.classModeScala
import quizleague.web.names.TeamNames

@Component(
  selector = "ql-team-list",
  template = s"""
  <div>
    <h2>Teams</h2>
    <div *ngFor="let item of items">
      <a routerLink="{{item.id}}" md-button>{{item.name}}</a>
    </div>
$addFAB
  </div>
  """    
)
@classModeScala
class TeamListComponent (
    override val service:TeamService,
    override val router: Router) 
   extends ListComponent[Team] with OnInit with TeamNames{
      
}