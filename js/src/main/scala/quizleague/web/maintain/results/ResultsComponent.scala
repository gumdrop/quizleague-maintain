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


@Component(
  template = s"""
 <div>
    <h2>Results {{item.fixtures.parentDescription}} {{item.fixtures.description}}</h2>
    <form #fm="ngForm" (submit)="save()">
    <div fxLayout="column">
       <div fxLayout="column">
        <h4>Result List</h4>
         <div fxLayout="column">
          <div *ngFor="let result of item.results;let i = index" fxLayout="row">
            <button md-icon-button type="button" (click)="removeResult(result)" ><md-icon class="md-24">delete</md-icon></button>
              <md-input-container>
                <input placeholder="{{result.fixture.home.name}}" [(ngModel)]="result.homeScore" name="home{{i}}">
              </md-input-container>
              <md-input-container>
                <input placeholder="{{result.fixture.away.name}}" [(ngModel)]="result.awayScore" name="away{{i}}">
              </md-input-container>               
          </div>
         </div>
        </div>      
     </div>
     $formButtons
    </form>
  </div>

  """    
)
@classModeScala
class ResultsComponent(
    override val service:ResultsService,
    override val route: ActivatedRoute,
    override val location:Location,
    val router:Router)
    extends ItemComponent[Results] with Logging{
  
    override def cancel():Unit = location.back()
    
    def removeResult(result:Result) = item.results -= result
}
    