package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.std.Component
import angulate2.core.OnInit
import quizleague.web.model._
import rxjs.Observable
import scalajs.js
import quizleague.web.site.season.SeasonService
import quizleague.web.util.rx._

@Component(
  template = s"""
  <div fxLayout="column" *ngFor="let competition of competitions | async">
    <a fxFlexAlign="start" routerLink="/competition/{{competition.id}}/{{competition.typeName}}"  md-menu-item routerLinkActive="active" >{{competition.name}}</a>
  </div>
  """    
)
class CompetitionMenuComponent(
    service:CompetitionService,
    viewService:CompetitionViewService,
    seasonService:SeasonService){
  
  val competitions = viewService.season.switchMap((s,i) => seasonService.get(s.id)).map((s,i) => sort[Competition](s.competitions,(c1,c2) => c1.name compareTo c2.name)).concatAll()

}