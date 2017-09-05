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
import quizleague.web.maintain.text.TextEditMixin


@Component(
  template = s"""
 <div *ngIf="item.fixture | async as fixture">
    <h2>Reports : {{fixture.date}} : {{(fixture.home | async)?.name}} vs item.{{(fixture.away | async)?.name}}</h2>
    <form #fm="ngForm" (submit)="save()">
     <div *ngIf="item.reports | async as reports" fxLayout="column">
      <div *ngFor="let report of reports.reports" fxLayout="row"><button (click)="editText(report.text)" md-button type="button">{{(report.team | async)?.shortName}}</button></div>
     </div>
     $formButtons
    </form>
  </div>

  """    
)
@classModeScala
class ReportListComponent(
    override val service:ResultService,
    override val route: ActivatedRoute,
    override val location:Location,
    val router:Router)
    extends ItemComponent[Result] with TextEditMixin[Result] with Logging
    