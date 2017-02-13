package org.chilternquizleague.web.maintain

import angulate2.std._
import angulate2.router.{Route, RouterModule}
import angulate2.platformBrowser.BrowserModule
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import org.chilternquizleague.web.maintain.venue.VenueModule
import org.chilternquizleague.web.maintain.user.UserModule


import scala.scalajs.js
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.web.maintain.team.TeamModule
import angular.flexlayout.FlexLayoutModule
import angulate2.ext.inMemoryWebApi.{InMemoryWebApiModule,InMemoryBackendConfigArgs}
import angulate2.http.HttpModule
import org.chilternquizleague.web.maintain.mock.MockData
import org.chilternquizleague.web.maintain.text.TextModule
import org.chilternquizleague.web.maintain.globaltext.GlobalTextModule
import org.chilternquizleague.web.maintain.applicationcontext.ApplicationContextModule
import org.chilternquizleague.web.maintain.season.SeasonModule
import org.chilternquizleague.web.maintain.competition.CompetitionModule

@NgModule(
  imports = @@[BrowserModule, FlexLayoutModule, AppRoutingModule , HttpModule,
    VenueModule, TeamModule, UserModule, TextModule, GlobalTextModule, ApplicationContextModule, SeasonModule] :+
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
          <a routerLink="/applicationContext" md-button >Application Context</a>
          <a routerLink="/globalText" md-button >Global Text</a>
          <a routerLink="/team" md-button >Teams</a>
          <a routerLink="/season" md-button >Seasons</a>
          <a routerLink="/user" md-button >Users</a>
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
