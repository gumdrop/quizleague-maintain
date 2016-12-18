package org.chilternquizleague.maintain.team

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import org.chilternquizleague.maintain.component.ItemComponent
import org.chilternquizleague.maintain.component.VenueGetter
import org.chilternquizleague.maintain.model._
import scala.scalajs.js
import org.chilternquizleague.maintain.venue.VenueService

@Component(
  selector = "ql-venue",
  template = """
  <div>
    <h2>Team Detail</h2>
    <form>
      <div fx-layout="column">
        <md-input placeholder="Name" type="text" id="name"
             required
             [(ngModel)]="item.name" name="name">
        </md-input>
        <md-input placeholder="Short Name" type="text" id="shortName"
             [(ngModel)]="item.shortName" name="shortName">
        </md-input>
        <select name="venue" [(ngModel)]="item.venue">
          <option *ngFor="let venue of venues;let i = index" [ngValue]="venue" >
            {{venue.name}}
          </option>
        </select> 
     </div>
    <button md-button (click)="save()">Save</button>
    </form>
  </div>
  """    
)
class TeamComponent(
    override val venueService:VenueService,
    override val service:TeamService,
    override val route: ActivatedRoute,
    override val location:Location) 
    extends ItemComponent[Team] 
    with VenueGetter{

}