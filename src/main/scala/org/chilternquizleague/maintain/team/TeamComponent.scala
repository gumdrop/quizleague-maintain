package org.chilternquizleague.maintain.team

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import org.chilternquizleague.maintain.component.ItemComponent
import org.chilternquizleague.maintain.component._
import org.chilternquizleague.maintain.model._
import scala.scalajs.js
import org.chilternquizleague.maintain.venue.VenueService
import angulate2.ext.classModeScala
import TemplateElements._

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
        <md-select placeholder="Venue" name="venue" [(ngModel)]="item.venue" required>
          <md-option *ngFor="let venue of venues" [value]="venue" >
            {{venue.name}}
          </md-option>
        </md-select>
        <label style="color: rgba(0,0,0,.38);">Users</label>
        <md-chip-list>
          <md-chip *ngFor="let user of item.users">{{user.name}}</md-chip> 
        </md-chip-list>
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
    override val location:Location) 
    extends ItemComponent[Team] {
  
  var venues:js.Array[Venue] = _
  
  override def ngOnInit() = super.ngOnInit();initVenues()
  
  def initVenues() = service.listVenues().subscribe(this.venues = _)
}
    