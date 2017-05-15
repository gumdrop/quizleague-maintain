package quizleague.web.site.fixtures

import rxjs.Observable.RichIObservable
import scala.scalajs.js.Any.fromString
import angulate2.router.ActivatedRoute
import angulate2.std._
import quizleague.web.site.season.SeasonService
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.SectionComponent
import quizleague.web.site.common.MenuComponent
import angulate2.ext.classModeScala
import quizleague.web.site.common.TitledComponent
import quizleague.web.site.common.TitleService

@Component(
  template = s"""
  <div fxLayout="column" fxLayoutGap="10px" style="margin-right:1em;">  
    <md-card *ngFor="let item of items | async">
      <md-card-title>{{item.parentDescription}} - {{item.date | date:"d MMMM yyyy"}} : {{item.description}}</md-card-title>
      <md-card-content>
        <ql-fixtures-simple [fixtures]="item.fixtures"></ql-fixtures-simple>
      </md-card-content>      
    </md-card>
  </div>
  """    
)
@classModeScala
class AllFixturesComponent(
    route:ActivatedRoute,
    seasonService:SeasonService,
    applicationContextService:ApplicationContextService,
    override val sideMenuService:SideMenuService,
    override val titleService:TitleService) extends SectionComponent with MenuComponent with TitledComponent{
  
  setTitle("All Fixtures")
  
  val items = applicationContextService.get().switchMap((ac,i) => seasonService.getFixtures(ac.currentSeason))
  
  
}