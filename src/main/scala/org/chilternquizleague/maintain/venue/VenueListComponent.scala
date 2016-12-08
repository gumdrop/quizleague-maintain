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
      <button md-button (click)="addNew()">Add</button>
    <div *ngFor="let item of items">
      <a routerLink="/venue/{{item.id}}" md-button>{{item.name}}</a>
    </div>
  </div>
  """    
)
class VenueListComponent (
    override val service:VenueService,
    override val router: Router) 
   extends ListComponent[Venue] with OnInit{
  
   override var items:js.Array[Venue] = _
  
   override def instance() = { 
     val id = UUID.randomUUID.toString;
     (id, Venue(id, "","","",""))
   }
  
   override def ngOnInit() = onInit()
   
   def addNew() = addNewItem()
   
   
}