package org.chilternquizleague.maintain.venue

import angulate2.std._
import org.chilternquizleague.maintain.model.Venue

import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.util.UUID

@Component(
  selector = "ql-venue-list",
  template = """
  <div>
  <div *ngFor="let item of values">
  <a routerLink="/venue/{{item.id}}" md-button>{{item.name}}</a>
  </div>
    <button md-button (click)="addNew()">Add</button>
  </div>
  """    
)
class VenueListComponent(service:VenueService) 
   extends OnInit {
  
  var values:js.Array[Venue] = _
  
  def addNew():Unit = {
    service.put(Venue(UUID.randomUUID.toString, "new Venue"))
    values = service.list.toJSArray
  }
  
  override def ngOnInit() = values = service.list.toJSArray
  
}