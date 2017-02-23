package quizleague.web.maintain.fixtures

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


@Component(
  template = s"""
  <div>
    <h2>Fixtures</h2>
    <form #fm="ngForm" (submit)="save()">
    <div fxLayout="column">
      <md-input-container>
        <input mdInput placeholder="Description" name="description" [(ngModel)]="item.description" type="text">
      </md-input-container>
     $formButtons
     </div>
    </form>
  </div>
  """    
)
@classModeScala
class FixturesComponent(
    override val service:FixturesService,
    override val route: ActivatedRoute,
    override val location:Location,
    val router:Router,
    val competitionService:CompetitionService)
    extends ItemComponent[Fixtures] with Logging{
  
    override def cancel():Unit = location.back()
    var comp:Competition = _
    
    override def init(): Unit = {
      route.params    
      .switchMap( (params,i) => competitionService.get(params("competitionId")) )
      .subscribe(comp = _)
      super.init()
    }
    
    override def save():Unit = {service.cache(item);location.back()}

}
    