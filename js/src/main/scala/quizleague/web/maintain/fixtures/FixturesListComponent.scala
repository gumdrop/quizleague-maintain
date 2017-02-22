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


@Component(
  template = s"""
  <div>
    <h2>Fixtures List</h2>
    <div *ngFor="let item of items">
      <a routerLink="fixtures/{{item.id}}" md-button>{{item.description}}</a>
    </div>
    <div style="position:absolute;right:1em;bottom:5em;">
      <button md-fab (click)="addNew()">
          <md-icon class="md-24">add</md-icon>
      </button>
    </div>
  </div>
  """    
)
@classModeScala
class FixturesListComponent(
    override val service:FixturesService,
    val route: ActivatedRoute,
    val location:Location,
    override val router:Router)
    extends ListComponent[Fixtures] with Logging with FixturesNames{
  
  override def ngOnInit() = {items = js.Array[Fixtures]()}

}
    