package quizleague.web.site.team

import angulate2.ext.classModeScala
import angulate2.std.Component
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.SectionComponent
import angulate2.router.ActivatedRoute
import rxjs.Observable
import quizleague.web.model.Team
import angulate2.core.OnInit
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._

@Component(
  template = s"""
  <ql-section-title *ngIf="itemObs | async as item; else loading">
     <span>
      {{item.name}}
    </span>
    <span style="flex:1 1 0;"></span>
    <span><a md-icon-button routerLink="/venue/{{item.venue.id}}" mdTooltip="Venue"><md-icon class="md-24">location_on</md-icon></a></span>
    $loadingTemplate
  </ql-section-title>
  """    
)
@classModeScala
class TeamTitleComponent(
    route:ActivatedRoute,  
    service:TeamService){  
  
  val itemObs = route.params.switchMap((params,i) => service.get(params("id")))
  
}