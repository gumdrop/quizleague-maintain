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
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._

@Component(
  template = s"""
  <div *ngIf="itemObs | async as item; else loading" fxLayout="column" fxLayoutGap="5px">
    <md-card>
      <md-card-title>All Fixtures</md-card-title>
      <md-card-content>
        <ql-fixtures-simple [fixtures]="fixtures" [inlineDetails]="true"></ql-fixtures-simple>
      </md-card-content>
    </md-card>
  </div>
  $loadingTemplate
  """    
)
@classModeScala
class TeamFixturesComponent(
    route:ActivatedRoute,
    service:TeamService,
    viewService:TeamViewService,
    applicationContextService:ApplicationContextService,
    override val titleService:TitleService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent with TitledComponent{
  
  
  
  val itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
  
  itemObs.subscribe(t => setTitle(s"${t.name} - Fixtures"))
  
  val fixtures = itemObs.switchMap((t,i) => viewService.season.switchMap((s,j) => viewService.getFixtures(t, s))) 
  
  
}

@Component(
  template = """<ql-team-sub-title text="Fixtures"></ql-team-sub-title>"""
)
class TeamFixturesTitleComponent