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

@Component(
  template = s"""
    <md-card *ngFor="let item of items | async">
      <md-card-title>{{item.fixtures.parentDescription}} {{item.fixtures.date}} {{item.fixtures.description}}</md-card-title>
        <table>
          <tr *ngFor="let result of item.results">
            <td>{{result.fixture.home.name}}</td> <td>{{result.homeScore}}</td><td> - </td><td>{{result.awayScore}}</td><td>{{result.fixture.away.name}}</td> 
          </tr>
        </table>      
    </md-card>
  """    
)
@classModeScala
class AllResultsComponent(
    route:ActivatedRoute,
    seasonService:SeasonService,
    applicationContextService:ApplicationContextService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent{
  
  
  val items = applicationContextService.get().switchMap((ac,i) => seasonService.getResults(ac.currentSeason))
  
  
}