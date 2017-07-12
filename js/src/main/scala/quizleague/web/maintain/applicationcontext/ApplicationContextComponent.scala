package quizleague.web.maintain.applicationcontext

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import quizleague.web.maintain.venue.VenueService
import angulate2.ext.classModeScala
import TemplateElements._
import quizleague.web.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import quizleague.web.model.GlobalText
import quizleague.web.site.common.SectionComponent
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.SideMenuService
import quizleague.web.maintain.season.SeasonService


@Component(
  template = s"""
  <div>
    <h2>Application Context</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
         <md-input-container>
         <input mdInput placeholder="League Name" type="text"
             required
             [(ngModel)]="item.leagueName" name="leagueName">
        </md-input-container>
        <select placeholder="Current Season" name="season" [(ngModel)]="item.currentSeason" required [compareWith]="utils.compareWith">
          <option *ngFor="let season of seasons | async" [ngValue]="season" >
            {{(season | async)?.startYear}}/{{(season | async)?.endYear}}
          </option>
        </select>
        <select placeholder="Global Text" name="globalText" [(ngModel)]="item.textSet" required [compareWith]="utils.compareWith">
          <option *ngFor="let textSet of textSets" [ngValue]="textSet" >
            {{textSet.name}}
          </option>
        </select>
        <md-input-container>        
          <input mdInput placeholder="Sender Email" type="text"
             required
             [(ngModel)]="item.senderEmail" name="senderEmail">
        </md-input-container>
        <label style="color: rgba(0,0,0,.38);">Email Aliases</label>
        <md-chip-list selectable>
          <md-chip *ngFor="let alias of item.emailAliases">{{alias.alias}} : {{alias.user.name}}
            <button md-icon-button (click)="removeAlias(alias)" type="button"><md-icon>delete</md-icon></button>
          </md-chip> 
        </md-chip-list>
     </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class ApplicationContextComponent(
    override val service:ApplicationContextService,
    override val route: ActivatedRoute,
    override val location:Location,
    override val sideMenuService:SideMenuService,
    val router:Router)
    extends ItemComponent[ApplicationContext] with SectionComponent with MenuComponent {
  
  var textSets:js.Array[GlobalText] = _
  
  var seasons = service.listSeasons()
  
  override def ngOnInit() = {super.ngOnInit();initTextSets}
  
  override def init() = service.get.subscribe(item = _)
  
  private def initTextSets() = service.listTextSets.subscribe(textSets = _)
  
  def removeAlias(alias:EmailAlias) = item.emailAliases -= alias
  
  
 
}
    