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
import quizleague.web.model.Season
import scalajs.js
import quizleague.web.site.common.TitledComponent
import quizleague.web.site.common.TitleService
import quizleague.web.util.Logging._
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._


@Component(
  template = s"""
    <div *ngIf="items | async as its; else loading" fxLayout="column" fxLayoutGap="5px">  
    <md-card *ngFor="let item of items | async">
      <md-card-title *ngIf="item.fixtures | async as fixtures">{{fixtures.parentDescription}} - {{fixtures.date | date:"d MMMM yyyy"}} : {{fixtures.description}}</md-card-title>
      <md-card-content>
        <ql-results-simple [list]="item.results"></ql-results-simple>
      </md-card-content>
      </md-card>
    </div>
    $loadingTemplate
  """    
)
@classModeScala
class AllResultsComponent(
    route:ActivatedRoute,
    seasonService:SeasonService,
    viewService:ResultsViewService,
    override val sideMenuService:SideMenuService,
    override val titleService:TitleService) extends SectionComponent with MenuComponent with TitledComponent with ComponentUtils{
  
  setTitle("All Results")
  
  val items = viewService.season.switchMap((s,i) => seasonService.getResults(s))
}

@Component(
  template = """
  <ql-section-title>
     <span>All Results</span><ql-season-select [currentSeason]="viewService.season | async" (onchange)="viewService.seasonChanged($event)"></ql-season-select>
  </ql-section-title>
  """    
)
class AllResultsTitleComponent(
  val viewService:ResultsViewService
)