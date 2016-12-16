package org.chilternquizleague.maintain

import angulate2.std._
import angulate2.router.{Route, RouterModule}
import angulate2.platformBrowser.BrowserModule
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import org.chilternquizleague.maintain.venue.VenueRoutesModule


import scala.scalajs.js
import angular.flexlayout.FlexLayoutModule

@NgModule(
  imports = @@[BrowserModule,VenueRoutesModule] :+
   RouterModule.forRoot(js.Array(
    Route(
      path = "",
      component = %%[RootComponent]
  ))) :+
  MaterialModule.forRoot() :+
  FlexLayoutModule.forRoot()
  ,
  declarations = @@[AppComponent,RootComponent],
  bootstrap = @@[AppComponent]
)
class AppModule 

@Component(
  selector = "ql-app",
  template = """
  <div>
   <md-toolbar color='primary'>

      <button md-icon-button (click)="sidenav.toggle()">
        <i class='material-icons app-toolbar-menu'>menu</i>
      </button>
       Chiltern Quiz League


    </md-toolbar>
    <md-sidenav-layout>
      <md-sidenav #sidenav mode="side" opened="true">
        <a routerLink="/venue" md-button >Venues</a>
      </md-sidenav>
      <div style="padding:1em;">
        <router-outlet></router-outlet>
      </div>
    </md-sidenav-layout>
  </div>
  """
)
class AppComponent

@Component(
  selector = "ql-root",
  template = """
  <div>
    <md-card>
      <md-card-title>Quiz League Maintenance App</md-card-title>
    </md-card>
  </div>
  """
)
class RootComponent 
