package quizleague.web.maintain.fixtures

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._
import quizleague.web.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import quizleague.web.util.Logging
import quizleague.web.maintain.competition.CompetitionService


@Component(
  template = s"""
  <div>
    <h2>{{comp.name}} Fixtures List</h2>
    <div *ngFor="let item of items">
      <a routerLink="{{item.id}}" mdButton>{{item.date.toDateString()}}</a>
    </div>
    $addFAB
   <div style="position:absolute;left:1em;bottom:5em;">
      <button md-fab (click)="back()">
          <md-icon class="md-24">arrow_back</md-icon>
      </button>
    </div>
  </div>
  """    
)
@classModeScala
class FixturesListComponent(
    override val service:FixturesService,
    val competitionService:CompetitionService,
    val route: ActivatedRoute,
    val location:Location,
    override val router:Router)
    extends ListComponent[Fixtures] with Logging with FixturesNames{
  
  override def ngOnInit() = init()
  
  var comp:Competition = _
  
  def init(): Unit = route.params
    .switchMap( (params,i) => competitionService.get(params("competitionId")) )
    .subscribe(x => {this.items = x.fixtures; comp = x})
    
  def back() = location.back()
    
    
  override def addNew():Unit = {
    val item = service.instance()
    comp.fixtures.push(item)
    competitionService.cache(comp)
    router.navigateRelativeTo(route,service.getId(item))
  }

}
    