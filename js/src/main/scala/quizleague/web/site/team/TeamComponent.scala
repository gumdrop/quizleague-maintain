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

@Component(
  template = s"""
  <div *ngIf="itemObs | async as item; else loading">
    <ql-text [textId]="item.text.id"></ql-text>
  </div>
  <ng-template #loading>Loading...</ng-template>
  """    
)
@classModeScala
class TeamComponent(
    route:ActivatedRoute,
    service:TeamService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent{
  
  
  val itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
  
  
}