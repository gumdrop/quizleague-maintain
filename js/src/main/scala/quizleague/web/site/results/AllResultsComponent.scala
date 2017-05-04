package quizleague.web.site.results

import angulate2.ext.classModeScala
import angulate2.std.Component
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.SectionComponent
import angulate2.router.ActivatedRoute
import rxjs.Observable
import quizleague.web.model.Team
import angulate2.core.OnInit
import quizleague.web.site.season.SeasonService
import quizleague.web.site.global.ApplicationContextService

@Component(
  template = s"""
  <div *ngIf="itemObs | async as item; else loading">
    <ql-text [textId]="item.text.id"></ql-text>
  </div>
  <ng-template #loading>Loading...</ng-template>
  """    
)
@classModeScala
class AllResultsComponent(
    route:ActivatedRoute,
    seasonService:SeasonService,
    applicationContextService:ApplicationContextService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent{
  
  
  val allResults = applicationContextService.get().switchMap((ac,i) => seasonService.getResults(ac.currentSeason))
  
  
}