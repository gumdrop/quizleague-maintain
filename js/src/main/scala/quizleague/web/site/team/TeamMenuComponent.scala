package quizleague.web.site.team

import angulate2.ext.classModeScala
import angulate2.std.Component
import angulate2.core.OnInit
import quizleague.web.model.Team
import rxjs.Observable
import scalajs.js

@Component(
  template = s"""
  <div *ngFor="let team of teams | async">
    <a routerLink="/team/{{team.id}}"  md-button routerLinkActive="active" >{{team.name}}</a>
  </div>
  """    
)
@classModeScala
class TeamMenuComponent(service:TeamService){
  
  val teams:Observable[js.Array[Team]] = service.list()

}