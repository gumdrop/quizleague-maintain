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
import quizleague.web.util.Logging
import rxjs.Subject
import quizleague.web.model.Result
import scalajs.js

@Component(
  template = s"""
  <div *ngIf="itemObs | async as item; else loading" fxLayout="column" fxLayoutGap="5px">
    <md-card>
      <md-card-title>All Results</md-card-title>
      <md-card-content>
        <ql-results-simple [results]="results | async" [inlineDetails]="true"></ql-results-simple>
      </md-card-content>
    </md-card>
  </div>
  <ng-template #loading>Loading...</ng-template>
  """    
)
@classModeScala
class TeamResultsComponent(
    route:ActivatedRoute,
    service:TeamService,
    viewService:TeamViewService,
    applicationContextService:ApplicationContextService,
    override val titleService:TitleService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent with TitledComponent with Logging{
  
  
  
  val itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
  
  itemObs.subscribe(t => setTitle(s"${t.name} - Results"))
  
  val results = viewService.season.switchMap((s,j) => itemObs.switchMap((t,i) => viewService.getResults(t, s)))
    
  results.subscribe(r => log(r, "Results"))
  
  
}

@Component(
  template = s"""
  <ql-section-title>
     <span *ngIf="itemObs | async as item; else loading">
      {{item.name}} - Results
    </span>
    <md-select placeholder="Season"></md-select>
    <ng-template #loading>Loading...</ng-template>
  </ql-section-title>
  """    
)
@classModeScala
class TeamResultsTitleComponent(
    route:ActivatedRoute,
    service:TeamService){  
  
  val itemObs = route.params.switchMap((params,i) => service.get(params("id")))
  
}