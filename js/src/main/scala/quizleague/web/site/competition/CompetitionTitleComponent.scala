package quizleague.web.site.competition

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
  <ql-section-title>
     <span *ngIf="itemObs | async as item; else loading">
      {{item.name}} <ql-season-name [season]="season"></ql-season-name>
    </span>
    <ng-template #loading>Loading...</ng-template>
  </ql-section-title>
  """    
)
@classModeScala
class CompetitionTitleComponent(
    route:ActivatedRoute,
    service:CompetitionService,
    viewService:CompetitionViewService){  
  
  val itemObs = route.params.switchMap((params,i) => service.get(params("id")))
  val season = viewService.season
  
}