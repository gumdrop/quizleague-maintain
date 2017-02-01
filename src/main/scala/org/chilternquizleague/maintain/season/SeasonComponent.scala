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
import org.chilternquizleague.maintain.text.TextEditMixin
import org.chilternquizleague.util.Logging

@Component(
  selector = "ql-season",
  template = s"""
  <div>
    <h2>Season Detail</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
        <md-input placeholder="Start Year" type="number"
             required
             [(ngModel)]="item.startYear" name="startYear">
        </md-input>
        <md-input placeholder="End Year" type="number"
             required
             [(ngModel)]="item.endYear" name="endYear">
        </md-input>
        <div fxLayout="row"><button (click)="editText(item.text)" md-button type="button" >Edit Text...</button></div>
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
    override val router:Router)
    extends ItemComponent[Season] with TextEditMixin[Season] with Logging{
  
  

}
    