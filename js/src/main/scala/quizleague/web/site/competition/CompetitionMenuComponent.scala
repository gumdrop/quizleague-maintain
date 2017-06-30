package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.std.Component
import angulate2.core.OnInit
import quizleague.web.model.Team
import rxjs.Observable
import scalajs.js
import quizleague.web.site.season.SeasonService
import quizleague.web.util.rx._

@Component(
  template = s"""
  <div *ngFor="let competition of competitions | async">
    <a routerLink="/competition/{{competition.id}}/{{competition.typeName}}"  md-button routerLinkActive="active" >{{competition.name}}</a>
  </div>
  """    
)
class CompetitionMenuComponent(
    service:CompetitionService,
    viewService:CompetitionViewService,
    seasonService:SeasonService){
  
  val competitions = viewService.season.switchMap((s,i) => seasonService.get(s.id)).map((s,i) => zip(s.competitions)).concatAll()

}