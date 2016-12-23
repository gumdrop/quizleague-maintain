package org.chilternquizleague.maintain.venue

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import org.chilternquizleague.maintain.model.Venue
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.component.ComponentNames
import org.chilternquizleague.maintain.component.IdStuff


@NgModule(
  imports = @@[BrowserModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule],
  declarations = @@[VenueComponent,VenueListComponent],
  providers = @@[VenueService]
   
)
class VenueModule

@Injectable
class VenueService(override val http:Http) extends EntityService[Venue] with VenueId{
  add(Venue(newId,"wibble","","",""))
}

trait VenueNames extends ComponentNames{
  override val typeName = "venue"
}

trait VenueId extends IdStuff[Venue]{
  override def getId(venue:Venue):String = venue.id
}