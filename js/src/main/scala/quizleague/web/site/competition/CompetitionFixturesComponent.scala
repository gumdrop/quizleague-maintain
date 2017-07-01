package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std._
import quizleague.web.site.common.{ MenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.site.global.ApplicationContextService
import scalajs.js
import quizleague.web.model.Results
import quizleague.web.model.Fixtures
import java.time.LocalDate
import quizleague.web.site.common.ComponentUtils
import quizleague.web.util.rx._

@Component(
  template = s"""
  <div fxLayout="column" fxLayoutGap="5px">
    <md-card *ngFor="let fixtures of filteredFixtures | async">
      <md-card-title>{{fixtures.date | date:"d MMM yyyy"}}</md-card-title>
      <md-card-content>
          <ql-fixtures-simple [list]="fixtures.fixtures" ></ql-fixtures-simple>
      </md-card-content>
    </md-card>
  </div>
  <ng-template #loading>Loading...</ng-template>
  """)
@classModeScala
class CompetitionFixturesComponent(
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
  
  val itemObs = route.params.switchMap((params, i) => service.get(params("id"))(4))

  val now = LocalDate.now().toString()
  val filteredFixtures = itemObs.switchMap((c,i) => zip(c.fixtures).map((fs,i) => fs.filter(f => f.date > now)))
  
  itemObs.subscribe(t => setTitle(s"${t.name} - All Fixtures"))
 
}

@Component(
  template = s"""
  <ql-section-title>
     <span *ngIf="itemObs | async as item; else loading">
      {{item.name}} Fixtures 
    </span>
    <ng-template #loading>Loading...</ng-template>
  </ql-section-title>
  """    
)
@classModeScala
class CompetitionFixturesTitleComponent(
    route:ActivatedRoute,
    service:CompetitionService){  
  
  val itemObs = route.params.switchMap((params,i) => service.get(params("id")))
  

}

   
