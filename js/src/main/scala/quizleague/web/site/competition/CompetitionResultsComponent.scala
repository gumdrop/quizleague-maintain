package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std._
import quizleague.web.site.common.{ MenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.site.global.ApplicationContextService
import scalajs.js
import quizleague.web.model._
import quizleague.web.util.rx._
import quizleague.web.site.common.ComponentUtils
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._
import rxjs.Observable

@Component(
  template = s"""
  <div fxLayout="column" fxLayoutGap="5px">
    <md-card *ngFor="let results of sortedResults | async">
      <md-card-title>{{(results.fixtures | async)?.date | date:"d MMM yyyy"}}</md-card-title>
      <md-card-content>
          <ql-results-simple [list]="results.results" ></ql-results-simple>
      </md-card-content>
    </md-card>
  </div>
  $loadingTemplate
  """)
@classModeScala
class CompetitionResultsComponent(
  route: ActivatedRoute,
  service: CompetitionService,
  viewService: CompetitionViewService,
  applicationContextService: ApplicationContextService,
  override val titleService: TitleService,
  override val sideMenuService: SideMenuService)
    extends SectionComponent
    with MenuComponent
    with TitledComponent
    with ComponentUtils{
  
  val itemObs = route.params.switchMap((params, i) => service.get(params("id")))

  itemObs.subscribe(t => setTitle(s"${t.name} - All Results"))
  
  val sortedResults = itemObs.switchMap((c,i) => sort2[Results,Fixtures](c.results, _.fixtures, (r1,r2) => r2._2.date compareTo r1._2.date))

}

@Component(
  template = s"""
  <ql-section-title>
     <span *ngIf="itemObs | async as item; else loading">
      {{item.name}} Results 
    </span>
    $loadingTemplate
  </ql-section-title>
  """    
)
@classModeScala
class CompetitionResultsTitleComponent(
    route:ActivatedRoute,
    service:CompetitionService){  
  
  val itemObs = route.params.switchMap((params,i) => service.get(params("id")))

  
}

   
