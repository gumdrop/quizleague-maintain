package quizleague.web.maintain.leaguetable

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent
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
import quizleague.web.model.LeagueCompetition
import quizleague.web.names.LeagueTableNames
import quizleague.web.maintain.component.ItemComponent._
import quizleague.web.util.rx._



@Component(
  template = s"""
  <div>
    <h2>{{comp.name}} Tables List</h2>
    <div *ngFor="let item of items;let i = index">
      <a routerLink="{{item.id}}" mdButton>{{i}} : {{item.description}}</a>
    </div>
    $addFAB
    $backFAB
  </div>
  """    
)
@classModeScala
class LeagueTableListComponent(
    override val service:LeagueTableService,
    val competitionService:CompetitionService,
    val route: ActivatedRoute,
    val location:Location,
    override val router:Router)
    extends ListComponent[LeagueTable] with Logging with LeagueTableNames{
  
  override def ngOnInit() = init()
  
  var comp:Competition = _
  
  override def sortit(a:LeagueTable,b:LeagueTable) = a.description compareTo b.description
  
  def init(): Unit = {
    val comps = route.params.switchMap( (params,i) => competitionService.get(params("competitionId")))
    comps.subscribe(comp = _)
    comps.switchMap((x,i) => sort(x.tables,sortit _)).subscribe((x:js.Array[LeagueTable]) => items = x)
  }
    
    
  override def addNew():Unit = {
    val item = log(service.instance(), "new table")
    service.cache(item)
    comp.tables +++= (item.id,item)
    competitionService.cache(comp)
    router.navigateRelativeTo(route,service.getId(item))
  }
    
  def back() = location.back()
   

}
    