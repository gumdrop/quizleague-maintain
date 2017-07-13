package quizleague.web.maintain.results

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent._
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._
import quizleague.web.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import quizleague.web.util.rx._


@Component(
  template = s"""
 <div>
    <h2>Results {{(item.fixtures | async)?.parentDescription}} {{(item.fixtures | async)?.description}}</h2>
    <form #fm="ngForm" (submit)="save()">
    <div fxLayout="column">
       <div fxLayout="column">
        <h4>Result List</h4>
         <div fxLayout="column">
          <div *ngFor="let r of item.results;let i = index" fxLayout="column">
              <div fxLayout="row" *ngIf="r | async as result">
                <button md-icon-button type="button" (click)="removeResult(result)" ><md-icon class="md-24">delete</md-icon></button>
                <md-input-container *ngIf="result.fixture | async as fixture">
                  <input mdInput placeholder="{{(fixture.home | async)?.name}}" [(ngModel)]="result.homeScore" name="home{{i}}" type="number">
                </md-input-container>
                <md-input-container *ngIf="result.fixture | async as fixture">
                  <input mdInput placeholder="{{(fixture.away | async)?.name}}" [(ngModel)]="result.awayScore" name="away{{i}}" type="number">
                </md-input-container>
                <button md-icon-button type="button" (click)="toggleNote(result)" mdTooltip="Note"><md-icon class="md-24">note</md-icon></button>
                <button md-icon-button type="button" (click)="showReports(result)" mdTooltip="Report" ><md-icon class="md-24">report</md-icon></button>
              </div>
              <div *ngIf="r | async as result">
                <md-input-container *ngIf="showNote(result)" >
                  <textarea mdInput placeholder="Notes" [(ngModel)]="result.note" name="note{{i}}" ></textarea>
                </md-input-container>
              </div>              
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
    val resultService:ResultService,
    override val route: ActivatedRoute,
    override val location:Location,
    val router:Router)
    extends ItemComponent[Results]{
  
  
    val showNotes = js.Array[Result]()  
    
    def results() = zip(item.results)
  
  
    override def cancel():Unit = location.back()
    
    
    
    def removeResult(result:Result) = item.results ---= result.id
    
    def toggleNote(result:Result) = if(showNotes.contains(result)) showNotes -= result else showNotes += result
     
    def showNote(result:Result) = showNotes.contains(result)
    def showReports(result:Result) = {
      service.cache(item)
      router.navigateRelativeTo(route,"result",result.id,"report")
    }
    
    override def save():Unit = {
      service.cache(item)
      location.back()
    }
}
    