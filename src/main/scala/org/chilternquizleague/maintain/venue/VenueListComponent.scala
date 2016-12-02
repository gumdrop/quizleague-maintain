package org.chilternquizleague.maintain.venue

import angulate2.std._
import org.chilternquizleague.maintain.model.Venue

import scala.scalajs.js

@Component(
  selector = "ql-venue-list",
  template = """
  <div>
  <div *ngFor="let item of values">
  {{item.name}}
  </div>
    <button md-button (click)="addNew()">Add</button>
  </div>
  """    
)
class VenueListComponent {
  
  var values:js.Array[Venue] = _
  
  def addNew():Unit = {values.push(Venue("1","new Venue"))}
  
}