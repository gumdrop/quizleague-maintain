package quizleague.web.site.results

import angulate2.std._
import angulate2.router.ActivatedRoute
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.TitleService
import quizleague.web.site.common.SectionComponent
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.TitledComponent
import quizleague.web.util.rx._
import angulate2.core.animations
import angulate2.core.animate
import angulate2.ext.classModeScala
import angulate2.common.Location
import quizleague.web.model._
import scalajs.js

@Component(
  template = """
    <div *ngIf="result | async as item; else loading">
      <div *ngFor="let report of item.reports" fxLayout="column">
        <md-card>
          <md-card-subtitle>By {{report.team.name}}</md-card-subtitle>
          <md-card-content>
            <ql-text [textId]="report.text.id"></ql-text>
          </md-card-content>
        </md-card>
      </div>  
    </div>
    <div style="position:absolute;left:1em;bottom:1em;">
      <button md-fab (click)="back()" mdTooltip="Back" mdTooltipPosition="above">
          <md-icon class="md-24">arrow_back</md-icon>
      </button>
    </div>
    <ng-template #loading>Loading...</ng-template>  
  """
)
@classModeScala
class ReportComponent(
    location:Location,
    route:ActivatedRoute,
    service:ResultService,
    override val sideMenuService:SideMenuService,
    override val titleService:TitleService) extends SectionComponent with MenuComponent with TitledComponent{
  
  val result = route.params.switchMap( (params,i) => service.get(params("id"))(4))
  
  setTitle(result.switchMap((r,i) => extract2[Result,Fixture,js.Array[Team],String](r, _.fixture, f => js.Array(f.home,f.away))((r,f,ts) => s"Reports for ${f.parentDescription} ${f.description} - ${ts(0).name} : ${ts(1).name}")))
  
  def back() = location.back()
}

@Component(
  template = """
    <div *ngIf="result | async as item; else loading">
      <ql-section-title><span>Reports for {{item.fixture.date | date:"d MMM yyyy"}} {{item.fixture.parentDescription}} {{item.fixture.description}} - {{item.fixture.home.name}} : {{item.fixture.away.name}}</span></ql-section-title>
    </div>
    <ng-template #loading>Loading...</ng-template> 
  """    
)
class ReportTitleComponent(
    route:ActivatedRoute,
    service:ResultService
){
  val result = route.params.switchMap( (params,i) => service.get(params("id"))(4))
}