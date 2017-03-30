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
import quizleague.web.names.FixturesNames


@Component(
  template = s"""
  <div>
    <h2>{{comp.name}} Fixtures List</h2>
    <div *ngFor="let item of items">
      <a routerLink="{{item.id}}" mdButton>{{item.date}}</a>
    </div>
    $addFAB
    $backFAB
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
  
  def sort(a:Fixtures,b:Fixtures) = a.date compareTo b.date
  
  def init(): Unit = route.params
    .switchMap( (params,i) => competitionService.get(params("competitionId"))(2) )
    .subscribe(x => {this.items = x.fixtures.sort(sort _); comp = x})
    
  def back() = location.back()
    
    
  override def addNew():Unit = {
    val item = service.instance(comp)
    comp.fixtures.push(item)
    competitionService.cache(comp)
    router.navigateRelativeTo(route,service.getId(item))
  }

}
    