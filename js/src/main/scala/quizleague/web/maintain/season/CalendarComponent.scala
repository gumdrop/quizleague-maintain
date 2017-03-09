package quizleague.web.maintain.season

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import js.JSConverters._
import quizleague.web.maintain.venue.VenueService
import angulate2.ext.classModeScala
import TemplateElements._
import quizleague.web.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import quizleague.web.maintain.text.TextEditMixin
import quizleague.web.util.Logging
import quizleague.web.model.CompetitionType
import quizleague.web.maintain.competition.CompetitionService

@Component(
  selector = "ql-season",
  template = s"""
  <div>
    <h2>Season Detail</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
        <md-input-container>
            <input mdInput placeholder="Start Year" type="number"
             required
             [(ngModel)]="item.startYear" name="startYear">
        </md-input-container>
        <md-input-container>        
          <input mdInput placeholder="End Year" type="number"
             required
             [(ngModel)]="item.endYear" name="endYear">
        </md-input-container>
        <div fxLayout="row"><button (click)="editText(item.text)" md-button type="button" >Edit Text...</button></div>
        <div fxLayout="row">
          <md-select placeholder="Competitions" [(ngModel)]="selectedType" name="selectedType">  
            <md-option *ngFor="let type of competitionTypes" [value]="type">{{type}}</md-option>
          </md-select>
          <button md-icon-button (click)="addCompetition(selectedType)" type="button" [disabled]="selectedType==null"><md-icon>add</md-icon></button>
        </div>
        <md-chip-list selectable="true">
          <md-chip *ngFor="let comp of item.competitions" ><button (click)="editCompetition(comp)" type="button">{{comp.name}}</button>
          </md-chip>
        </md-chip-list>

      </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class CalendarComponent(
    override val service:SeasonService,
    override val route: ActivatedRoute,
    override val location:Location,
    override val router:Router)
    extends ItemComponent[Season] with TextEditMixin[Season] with Logging{
  
  

}
    