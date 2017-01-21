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

@Component(
  selector = "ql-team",
  template = """
  <div>
    <h2>Team Detail</h2>
    <form>
      <div fxLayout="column">
        <md-input placeholder="Name" type="text" id="name"
             required
             [(ngModel)]="item.name" name="name">
        </md-input>
        <md-input placeholder="Short Name" type="text" id="shortName"
             [(ngModel)]="item.shortName" name="shortName">
        </md-input>
        <md-select placeholder="Venue" name="venue" [(ngModel)]="item.venue" required>
          <md-option *ngFor="let venue of venues" [value]="venue" >
            {{venue.name}}
          </md-option>
        </md-select> 
     </div>
      <div fxLayout="row">
        <button md-button (click)="save()" submit>Save</button>
        <button md-button (click)="cancel()" submit>Cancel</button>
      </div>
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
    