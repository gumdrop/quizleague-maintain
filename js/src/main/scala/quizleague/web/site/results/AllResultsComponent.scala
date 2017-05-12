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
import quizleague.web.model.Results
import scalajs.js
import quizleague.web.site.common.TitledComponent
import quizleague.web.site.common.TitleService

@Component(
  template = s"""
    <div fxLayout="column" fxLayoutGap="5px">  
    <md-card *ngFor="let item of items | async">
      <md-card-title>{{item.fixtures.parentDescription}} - {{item.fixtures.date | date:"d MMMM yyyy"}} : {{item.fixtures.description}}</md-card-title>
        <ql-results-simple [results]="item.results"></ql-results-simple>
      </md-card>
    </div>
  """    
)
@classModeScala
class AllResultsComponent(
    route:ActivatedRoute,
    seasonService:SeasonService,
    applicationContextService:ApplicationContextService,
    override val sideMenuService:SideMenuService,
    override val titleService:TitleService) extends SectionComponent with MenuComponent with TitledComponent{
  
  setTitle("All Results")
  
  val items = applicationContextService.get().switchMap((ac,i) => seasonService.getResults(ac.currentSeason))
  
  
}