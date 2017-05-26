package quizleague.web.site.team

import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std._
import quizleague.web.site.common.{ MenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.util.Logging
import angulate2.core.Input
import scala.scalajs.js.annotation.JSExport


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

  
  
}

@Component(
  template = """<ql-team-sub-title text="Results"></ql-team-sub-title>"""
)
class TeamResultsTitleComponent


@Component(
  selector = "ql-team-sub-title",
  template = """
  <ql-section-title>
     <span *ngIf="itemObs | async as item; else loading">
      {{item.name}} - {{text}}
    </span>
    <ql-season-select [currentSeason]="viewService.season | async" (onchange)="viewService.seasonChanged($event)"></ql-season-select>
    <ng-template #loading>Loading...</ng-template>
  </ql-section-title>
  """
)
class TeamSubTitleComponent(
    route:ActivatedRoute,
    service:TeamService,
    val viewService:TeamViewService){  
  
  @Input
  var text:String = _
  
  val itemObs = route.params.switchMap((params,i) => service.get(params("id")))
  
}