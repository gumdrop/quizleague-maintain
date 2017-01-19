package org.chilternquizleague.maintain

import angulate2.std._
import angulate2.router.{Route, RouterModule}
import angulate2.platformBrowser.BrowserModule
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import org.chilternquizleague.maintain.venue.VenueModule


import scala.scalajs.js
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.maintain.team.TeamModule
import angular.flexlayout.FlexLayoutModule
import angulate2.ext.inMemoryWebApi.{InMemoryWebApiModule,InMemoryBackendConfigArgs}
import angulate2.http.HttpModule
import org.chilternquizleague.maintain.mock.MockData

@NgModule(
  imports = @@[BrowserModule,VenueModule, TeamModule, FlexLayoutModule, AppRoutingModule , HttpModule] :+
  InMemoryWebApiModule.forRoot(%%[MockData],InMemoryBackendConfigArgs(delay = 0)) :+
  MaterialModule.forRoot() :+
  FlexLayoutModule.forRoot()
  ,
  declarations = @@[AppComponent,RootComponent],
  bootstrap = @@[AppComponent]
)
class AppModule 

@Routes(
  root = true,
  Route(path = "", component = %%[RootComponent])
)
class AppRoutingModule

@Component(
  selector = "ql-app",
  template = """
  <div>
   <md-toolbar color='primary'>
      <button md-icon-button (click)="sidenav.toggle()">
        <i class='material-icons app-toolbar-menu'>menu</i>
      </button>
      Quiz League website maintenance
    </md-toolbar>
    <md-sidenav-container>
      <md-sidenav #sidenav mode="side" opened="true">
        <div  fxLayout="column">
          <a routerLink="/team" md-button >Teams</a>
          <a routerLink="/venue" md-button >Venues</a>
        </div>
      </md-sidenav>
      <div style="padding:1em;">
        <router-outlet></router-outlet>
      </div>
    </md-sidenav-container>
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
