package org.chilternquizleague.maintain.team

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import org.chilternquizleague.maintain.component.ItemComponent
import org.chilternquizleague.maintain.component._
import org.chilternquizleague.web.model._
import scala.scalajs.js
import org.chilternquizleague.maintain.venue.VenueService
import angulate2.ext.classModeScala
import TemplateElements._
import org.chilternquizleague.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import org.chilternquizleague.maintain.text.TextEditMixin

@Component(
  selector = "ql-team",
  template = s"""
  <div>
    <h2>Team Detail</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
        <md-input placeholder="Name" type="text" id="name"
             required
             [(ngModel)]="item.name" name="name">
        </md-input>
        <md-input placeholder="Short Name" type="text" id="shortName" required
             [(ngModel)]="item.shortName" name="shortName">
        </md-input>
        <md-select placeholder="Venue" name="venue" [(ngModel)]="item.venue" required >
          <md-option *ngFor="let venue of venues" [value]="venue" >
            {{venue.name}}
          </md-option>
        </md-select>
        <label style="color: rgba(0,0,0,.38);">Users</label>
        <md-chip-list selectable="true">
          <md-chip *ngFor="let user of item.users">{{user.name}}
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
  
  var venues:js.Array[Venue] = _
  var users:js.Array[User] = _
  
  def trackVenue(item1:Venue, item2:Venue) = item1.id == item2.id
  override def ngOnInit() = {super.ngOnInit();initVenues;initUsers}
  
  private def initVenues() = service.listVenues.subscribe(venues = _)
  private def initUsers() = service.listUsers.subscribe(users = _)
  
  def removeUser(user:User) = item.users -= user
}
    