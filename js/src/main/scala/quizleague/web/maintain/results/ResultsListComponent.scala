package quizleague.web.maintain.results

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
import quizleague.web.names.ResultsNames
import quizleague.web.util.rx._
import rxjs.Observable



@Component(
  template = s"""
  <div>
    <h2>{{(comp | async).name}} Results List</h2>
    <div *ngFor="let item of items">
      <a routerLink="{{item.id}}" mdButton>{{(item.fixtures | async).date}}</a>
    </div>
   $backFAB
  </div>
  """    
)
@classModeScala
class ResultsListComponent(
    override val service:ResultsService,
    val competitionService:CompetitionService,
    val route: ActivatedRoute,
    val location:Location,
    override val router:Router)
    extends ListComponent[Results] with Logging with ResultsNames{
  
  override def ngOnInit() = init()
  
  var comp:Observable[Competition] = _
  
  def sort(a:(Results,Fixtures),b:(Results,Fixtures)) = a._2.date compareTo b._2.date
  
  def init(): Unit = {
    comp = route.params.switchMap( (params,i) => competitionService.get(params("competitionId")))
    
    comp.switchMap((c,i) => c.results)
    .switchMap((rs,i) => Observable.zip(rs.map(r => r.fixtures.obs.map((f,i) => (r,f))):_*)) 
    .subscribe(x => {items = x.sort(sort _).map(_._1)})
  }
    

    
  def back() = location.back()
   

}
    