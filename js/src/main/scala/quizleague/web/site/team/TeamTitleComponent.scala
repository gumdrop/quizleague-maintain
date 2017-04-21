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
  <div>Team</div> 
  <md-toolbar>
     <span *ngIf="itemObs | async as item; else loading">
      Team : {{item.name}}
    </span>
    <ng-template #loading>Loading...</ng-template>
  </md-toolbar>
  """    
)
@classModeScala
class TeamTitleComponent(
    route:ActivatedRoute,
    service:TeamService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent with OnInit{
  
  
  var itemObs:Observable[Team] = _
  
  override def ngOnInit() = {
    itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
  }
  
}