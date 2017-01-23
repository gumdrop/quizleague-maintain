package org.chilternquizleague.maintain.venue

import angulate2.std._
import angulate2.router.ActivatedRoute
import org.chilternquizleague.maintain.model.Venue
import angulate2.common.Location
import org.chilternquizleague.maintain.component._
import scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._

@Component(
  selector = "ql-venue",
  template = s"""
  <div>
    <h2>Venue Detail</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
        <md-input placeholder="Name" type="text"
             required
             [(ngModel)]="item.name" name="name">
        </md-input>
        <md-input placeholder="Phone" type="phone"
             [(ngModel)]="item.phone" name="phone">
        </md-input>
        <md-input placeholder="Email" type="email"
             [(ngModel)]="item.email" name="email">
        </md-input>
        <md-input placeholder="Website" type="url"
             [(ngModel)]="item.website" name="website">
        </md-input>
        <md-input placeholder="Image URL" type="url"
             [(ngModel)]="item.imageURL" name="imageURL">
        </md-input> 
        $chbxRetired
     </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class VenueComponent(
    override val service:VenueService,
    override val route: ActivatedRoute,
    override val location:Location) extends ItemComponent[Venue] 