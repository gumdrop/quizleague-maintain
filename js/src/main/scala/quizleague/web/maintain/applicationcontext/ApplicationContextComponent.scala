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
        <md-select placeholder="Global Text" name="globalText" [(ngModel)]="item.textSet" required >
          <md-option *ngFor="let textSet of textSets" [value]="textSet" >
           - {{textSet.name}}
          </md-option>
        </md-select>
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
    val router:Router)
    extends ItemComponent[ApplicationContext] {
  
  var textSets:js.Array[GlobalText] = _
  
  override def ngOnInit() = {super.ngOnInit();initTextSets}
  
  override def init() = service.get.subscribe(item = _)
  
  private def initTextSets() = service.listTextSets.subscribe(textSets = _)
  
  def removeAlias(alias:EmailAlias) = item.emailAliases -= alias
  
  
 
}
    