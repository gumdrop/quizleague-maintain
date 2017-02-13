package org.chilternquizleague.maintain.venue

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import org.chilternquizleague.web.model.Venue
import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.web.util.UUID
import org.chilternquizleague.maintain.component.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import rxjs.Observable


@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule,VenueRoutesModule],
  declarations = @@[VenueComponent,VenueListComponent],
  providers = @@[VenueService]
   
)
class VenueModule

@Routes(
  root = false,
  Route(
    path = "venue/:id",
    component = %%[VenueComponent]
  ),
  Route(
    path = "venue",
    component = %%[VenueListComponent]
  )
)
class VenueRoutesModule 


trait VenueNames extends ComponentNames{
  override val typeName = "venue"
}

