package org.chilternquizleague.maintain.season

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
import org.chilternquizleague.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }

@Component(
  selector = "ql-team",
  template = s"""
  <div>
    <h2>Season Detail</h2>
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
        <md-chip-list>
          <md-chip *ngFor="let user of item.users">{{user.name}}</md-chip> 
        </md-chip-list>
        <div fxLayout="row"><button (click)="editText(item)" md-button type="button" >Edit Text...</button></div>
        $chbxRetired
     </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class SeasonComponent(
    override val service:SeasonService,
    override val route: ActivatedRoute,
    override val location:Location,
    val router:Router)
    extends ItemComponent[Season] {
  
  var venues:js.Array[Venue] = _
  var users:js.Array[User] = _
  
  def editText(season:Season) = {
    service.cache(season)
    router.navigateTo("/text", season.text.id)
  }
  

}
    