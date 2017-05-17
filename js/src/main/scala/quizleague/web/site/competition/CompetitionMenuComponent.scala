package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.std.Component
import angulate2.core.OnInit
import quizleague.web.model.Team
import rxjs.Observable
import scalajs.js

@Component(
  template = s"""
<div>Menu</div>  
  <div *ngFor="let competition of competitions | async">
    <a routerLink="/competition/{{competition.id}}/{{competition.typeName}}"  md-button routerLinkActive="active" >{{competition.name}}</a>
  </div>
  """    
)
class CompetitionMenuComponent(
    service:CompetitionService,
    viewService:CompetitionViewService){
  
  val competitions = viewService.season.map((s,i) => s.competitions)

}