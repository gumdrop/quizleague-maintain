package quizleague.web.maintain.team

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component.ItemComponent._
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import quizleague.web.maintain.venue.VenueService
import angulate2.ext.classModeScala
import TemplateElements._
import quizleague.web.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import quizleague.web.maintain.text.TextEditMixin

@Component(
  selector = "ql-team",
  template = s"""
  <div>
    <h2>Team Detail</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
        <md-input-container>
        <input mdInput placeholder="Name" type="text" id="name"
             required
             [(ngModel)]="item.name" name="name">
        </md-input-container>
        <md-input-container>
        <input mdInput placeholder="Short Name" type="text" id="shortName" required
             [(ngModel)]="item.shortName" name="shortName">
        </md-input-container>
        <md-select placeholder="Venue" name="venue" [(ngModel)]="item.venue" required >
          <md-option *ngFor="let venue of venues | async" [value]="venue" >
            {{venue.name}}
          </md-option>
        </md-select>
        <label style="color: rgba(0,0,0,.38);">Users</label>
        <md-chip-list selectable="true">
          <md-chip *ngFor="let user of item.users">{{(user | async).name}}
            <button md-icon-button (click)="removeUser(user)" type="button"><md-icon>delete</md-icon></button>
          </md-chip> 
        </md-chip-list>
        <div fxLayout="row"><button (click)="editText(item.text)" md-button type="button" >Edit Text...</button></div>
        $chbxRetired
     </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class TeamComponent(
    override val service:TeamService,
    override val route: ActivatedRoute,
    override val location:Location,
    override val router:Router)
    extends ItemComponent[Team] with TextEditMixin[Team]{
  
  val venues = service.listVenues
  val users = service.listUsers
  
  def trackVenue(item1:Venue, item2:Venue) = item1.id == item2.id
  
  def removeUser(user:User) = item.users ---= user.id
}
    