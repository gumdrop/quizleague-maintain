package org.chilternquizleague.maintain.venue

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

@NgModule(
  imports = @@[BrowserModule,FormsModule,MaterialModule],
  declarations = @@[VenueComponent,VenueListComponent]
   
)
class VenuesModule {
  
}