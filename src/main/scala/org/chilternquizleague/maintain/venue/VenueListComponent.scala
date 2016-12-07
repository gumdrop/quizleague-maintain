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
  <div *ngFor="let item of items">
  <a routerLink="/venue/{{item.id}}" md-button>{{item.name}}</a>
  </div>
    <button md-button (click)="addNew()">Add</button>
  </div>
  """    
)
class VenueListComponent(
    override val service:VenueService,
    override val router: Router) 
   extends ListComponent[Venue]{
  
   override def instance() = { 
     val id = UUID.randomUUID.toString;
     (id, Venue(id, "","","",""))
   }
}