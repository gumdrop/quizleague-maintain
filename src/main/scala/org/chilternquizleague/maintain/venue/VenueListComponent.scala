package org.chilternquizleague.maintain.venue

import angulate2.std._
import org.chilternquizleague.maintain.model.Venue

import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.util.UUID
import angulate2.router.Router
import org.chilternquizleague.maintain.component.ListComponent
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.model.Venue

@Component(
  selector = "ql-venue-list",
  template = """
  <div>
    <h2>Venues</h2>
    <div *ngFor="let item of items">
      <a routerLink="/venue/{{item.id}}" md-button>{{item.name}}</a>
    </div>
    <div style="position:absolute;right:1em;bottom:5em;">
      <button md-fab (click)="addNew()">
          <md-icon class="md-24">add</md-icon>
      </button>
    </div>
  </div>
  """    
)
class VenueListComponent (
    override val service:VenueService,
    override val router: Router) 
   extends ListComponent[Venue] with OnInit with VenueNames with VenueId{
  
   override def instance() = Venue(newId, "","","","")

}