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


@Component(
  template = s"""
  <div>
    <h2>Fixtures</h2>
    <form #fm="ngForm" (submit)="save()">
      <div [ngSwitch]="'league'">
        <ql-league-competition *ngSwitchCase="league" item="item"></ql-league-competition>
        <!--ql-cup-competition *ngSwitchCase="cup"></ql-cup-competition>
        <ql-subsidiary-competition *ngSwitchCase="subsidiary"></ql-subsidiary-competition-->
        <div *ngSwitchDefault>No match Found</div>
      </div>
      <div fxLayout="row"><button (click)="editText(item.text)" md-button type="button" >Edit Text...</button></div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class FixturesComponent(
    override val service:FixturesService,
    override val route: ActivatedRoute,
    override val location:Location,
    val router:Router)
    extends ItemComponent[Fixtures] with Logging{
  
    override def cancel():Unit = location.back()
}
    