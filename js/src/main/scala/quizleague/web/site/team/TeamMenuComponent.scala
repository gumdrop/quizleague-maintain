package quizleague.web.site.team

import angulate2.ext.classModeScala
import angulate2.std.Component
import angulate2.core.OnInit
import quizleague.web.model.Team
import rxjs.Observable
import scalajs.js

@Component(
  template = s"""
  <div fxLayout="column" *ngFor="let team of teams | async">
    <a fxFlexAlign="start" routerLink="/team/{{team.id}}"  md-menu-item routerLinkActive="active" >{{team.name}}</a>
  </div>
  """    
)
@classModeScala
class TeamMenuComponent(service:TeamService){
  
  val teams:Observable[js.Array[Team]] = service.list().map((t,i) => t.sort((t1:Team,t2:Team) => t1.shortName compareTo t2.shortName))

}