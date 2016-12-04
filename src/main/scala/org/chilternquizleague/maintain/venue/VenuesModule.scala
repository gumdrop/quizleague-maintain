package org.chilternquizleague.maintain.venue

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import org.chilternquizleague.maintain.model.Venue
import rxjs.core.Promise
import rxjs.RxPromise

@NgModule(
  imports = @@[BrowserModule,FormsModule,MaterialModule,RouterModule],
  declarations = @@[VenueComponent,VenueListComponent],
  providers = @@[VenueService]
   
)
class VenuesModule {
  
}

@Injectable
class VenueService {
  var venues:Map[String,Venue] = Map()
  
  def put(venue:Venue):Venue = {venues = venues + ((venue.id, venue));venue}
  def get(id:String) = RxPromise.resolve(venues(id))
  def list() = venues.values
}