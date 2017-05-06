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
      <md-card-title>{{item.parentDescription}} {{item.date}} {{item.description}}</md-card-title>
      <table>
        <tr *ngFor="let fixture of item.fixtures">
          <td>{{fixture.home.name}}</td><td> - </td><td>{{fixture.away.name}}</td> 
        </tr>
      </table>      
    </md-card>
  """    
)
@classModeScala
class AllFixturesComponent(
    route:ActivatedRoute,
    seasonService:SeasonService,
    applicationContextService:ApplicationContextService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent{
  
  
  val items = applicationContextService.get().switchMap((ac,i) => seasonService.getFixtures(ac.currentSeason))
  
  
}