package org.chilternquizleague.maintain.component

import org.chilternquizleague.maintain.model.Ref
import org.chilternquizleague.maintain.venue.VenueService
import rxjs.Operators
import org.chilternquizleague.maintain.model.Venue
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import angulate2.core.OnInit

trait VenueGetter extends OnInit{
  
  this:ItemComponent[_] =>
  
  val venueService:VenueService
  
  @JSExport
  var venues:js.Array[Venue] = _
  
  @JSExport
  override def ngOnInit() = init();initVenues()
  
  def initVenues() = venueService.list.subscribe(this.venues = _)
  
}