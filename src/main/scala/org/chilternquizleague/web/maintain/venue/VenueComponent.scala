package org.chilternquizleague.web.maintain.venue

import angulate2.std._
import angulate2.router.ActivatedRoute
import org.chilternquizleague.web.model.Venue
import angulate2.common.Location
import org.chilternquizleague.web.maintain.component._
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
        <md-input-container>
        <input mdInput placeholder="Name" type="text"
             required
             [(ngModel)]="item.name" name="name">
        </md-input-container>
        <md-input-container>
        <md-placeholder>Phone</md-placeholder>
        <input mdInput  type="phone"
             [(ngModel)]="item.phone" name="phone">
        </md-input-container>
        <md-input-container>
        <input mdInput placeholder="Email" type="email"
             [(ngModel)]="item.email" name="email">
        </md-input-container>
        <md-input-container>
        <input mdInput placeholder="Website" type="url"
             [(ngModel)]="item.website" name="website">
        </md-input-container>
        <md-input-container>
        <input mdInput placeholder="Image URL" type="url"
             [(ngModel)]="item.imageURL" name="imageURL">
        </md-input-container> 
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