package org.chilternquizleague.maintain.venue

import angulate2.std._
import angulate2.router.ActivatedRoute
import org.chilternquizleague.maintain.model.Venue
import angulate2.common.Location
import org.chilternquizleague.maintain.component.ItemComponent

@Component(
  selector = "ql-venue",
  template = """
  <div>
    <h2>Venue Detail</h2>
    <form>
      <div fx-layout="column">
        <md-input placeholder="Name" type="text" id="name"
             required
             [(ngModel)]="item.name" name="name">
        </md-input>
        <md-input placeholder="Phone" type="phone" id="phone"
    
             [(ngModel)]="item.phone" name="phone">
        </md-input>
        <md-input placeholder="Email" type="email" id="email"
    
             [(ngModel)]="item.email" name="email">
        </md-input>
        <md-input placeholder="Website" type="url" id="website"
    fx-layout
             [(ngModel)]="item.website" name="website">
        </md-input>
      </div>
    <button md-button (click)="save()">Save</button>
    </form>
  </div>
  """    
)
class VenueComponent(
    override val service:VenueService,
    override val route: ActivatedRoute,
    override val location:Location) extends ItemComponent[Venue] 