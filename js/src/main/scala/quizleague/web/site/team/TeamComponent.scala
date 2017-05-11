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
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.season.SeasonService
import quizleague.web.site.common.TitleService
import quizleague.web.site.common.TitledComponent

@Component(
  template = s"""
  <div *ngIf="itemObs | async as item; else loading" fxLayout="column" fxLayoutGap="5px">
    <ql-text [textId]="item.text.id"></ql-text>
    <md-card>
      <md-card-title>Results</md-card-title>
      <ql-results-simple [results]="results | async"></ql-results-simple>
    </md-card>
    <md-card>
      <md-card-title>Fixtures</md-card-title>
      <ql-fixtures-simple [fixtures]="fixtures | async"></ql-fixtures-simple>
    </md-card>
  </div>
  <ng-template #loading>Loading...</ng-template>
  """    
)
@classModeScala
class TeamComponent(
    route:ActivatedRoute,
    service:TeamService,
    viewService:TeamViewService,
    applicationContextService:ApplicationContextService,
    override val titleService:TitleService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent with TitledComponent{
  
  
  
  val itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
  
  itemObs.subscribe(t => setTitle(t.name))
  
  val results = itemObs.switchMap((t,i) => applicationContextService.get().switchMap((ac,j) => viewService.getResults(t, ac.currentSeason))) 
  
  val fixtures = itemObs.switchMap((t,i) => applicationContextService.get().switchMap((ac,j) => viewService.getFixtures(t, ac.currentSeason))) 
  
  
}