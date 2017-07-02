package quizleague.web.maintain.season

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component.ItemComponent._
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
        <div fxLayout="row"><button (click)="calendar()" md-button type="button" >Calendar...</button></div>
        <div fxLayout="row">
          <md-select placeholder="Competitions" [(ngModel)]="selectedType" name="selectedType">  
            <md-option *ngFor="let type of competitionTypes" [value]="type">{{type}}</md-option>
          </md-select>
          <button md-icon-button (click)="addCompetition(selectedType)" type="button" [disabled]="selectedType==null"><md-icon>add</md-icon></button>
        </div>
        <md-chip-list selectable="true">
          <md-chip *ngFor="let comp of item.competitions" >
            <button *ngIf="comp | async as c" (click)="editCompetition(c)" type="button">{{c.name}}</button>
          </md-chip>
        </md-chip-list>

      </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class SeasonComponent(
    override val service:SeasonService,
    override val route: ActivatedRoute,
    override val location:Location,
    override val router:Router,
    competitionService:CompetitionService)
    extends ItemComponent[Season] with TextEditMixin[Season] with Logging{
  
  
    def addCompetition(typeName:String) = {
      val comp:Competition = competitionService.instance(CompetitionType.withName(typeName))
      item.competitions +++= (comp.id,comp)
      editCompetition(comp)
    }
  
    def editCompetition(comp: Competition) = {
      service.cache(item)
      router.navigateRelativeTo(route, "competition", comp.id, comp.typeName)
    }
    
    def calendar() = {
      service.cache(item)
      router.navigateRelativeTo(route, "calendar")
    }
    
    lazy val competitionTypes = CompetitionType.values.map(_.toString).toJSArray
    var selectedType:String = _
}
    