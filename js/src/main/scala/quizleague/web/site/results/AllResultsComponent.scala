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


@Component(
  template = s"""
    <div fxLayout="column" fxLayoutGap="5px">  
    <md-card *ngFor="let item of items() | async">
      <md-card-title>{{item.fixtures.parentDescription}} - {{item.fixtures.date | date:"d MMMM yyyy"}} : {{item.fixtures.description}}</md-card-title>
      <md-card-content>
        <ql-results-simple [results]="item.results"></ql-results-simple>
      </md-card-content>
      </md-card>
    </div>
  """    
)
@classModeScala
class AllResultsComponent(
    route:ActivatedRoute,
    seasonService:SeasonService,
    viewService:ResultsViewService,
    override val sideMenuService:SideMenuService,
    override val titleService:TitleService) extends SectionComponent with MenuComponent with TitledComponent{
  
  setTitle("All Results")
  
  def items() = Observable.of(viewService.season).filter((s,i) => s != null).switchMap((s,i) => seasonService.getResults(s))
}

@Component(
  template = """
  <ql-section-title>
     <span>All Results</span><ql-season-select [currentSeason]="viewService.season" (onchange)="seasonChanged($event)"></ql-season-select>
  </ql-section-title>
  """    
)
class AllResultsTitleComponent(
  val viewService:ResultsViewService
){

  def seasonChanged(s:Season) = {
    //import scala.language.reflectiveCalls
    log(s, "season changed");
    viewService.season = s
    
  }
}