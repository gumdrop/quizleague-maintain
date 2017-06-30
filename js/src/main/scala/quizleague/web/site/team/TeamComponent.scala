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
      <md-card-subtitle>Last few results</md-card-subtitle>
      <md-card-content>
        <ql-results-simple [results]="results | async" [inlineDetails]="true"></ql-results-simple>
      </md-card-content>
      <md-card-actions>
        <a md-button routerLink="results">Show All</a>
      </md-card-actions>
    </md-card>
    <md-card>
      <md-card-title>Fixtures</md-card-title>
      <md-card-subtitle>Next few fixtures</md-card-subtitle>
      <md-card-content>
        <ql-fixtures-simple [fixtures]="fixtures | async" [inlineDetails]="true"></ql-fixtures-simple>
      </md-card-content>
      <md-card-actions>
        <a md-button routerLink="fixtures">Show All</a>
      </md-card-actions>
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
  
  val results = itemObs.switchMap((t,i) => applicationContextService.get().switchMap((ac,i) => ac.currentSeason.obs).switchMap((s,j) => viewService.getResults(t, s))) 
  
  val fixtures = itemObs.switchMap((t,i) => applicationContextService.get().switchMap((ac,i) => ac.currentSeason.obs).switchMap((s,j) => viewService.getFixtures(t, s))) 
  
  
}