package quizleague.web.maintain

import angulate2.std._
import angulate2.router.{Route, RouterModule}
import angulate2.platformBrowser.BrowserModule
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import quizleague.web.maintain.venue.VenueModule
import quizleague.web.maintain.user.UserModule


import scala.scalajs.js
import angular.flexlayout.FlexLayoutModule
import quizleague.web.maintain.team.TeamModule
import angular.flexlayout.FlexLayoutModule
import angulate2.ext.inMemoryWebApi.{InMemoryWebApiModule,InMemoryBackendConfigArgs}
import angulate2.http.HttpModule
import quizleague.web.maintain.mock.MockData
import quizleague.web.maintain.text.TextModule
import quizleague.web.maintain.globaltext.GlobalTextModule
import quizleague.web.maintain.applicationcontext.ApplicationContextModule
import quizleague.web.maintain.season.SeasonModule
import quizleague.web.maintain.competition.CompetitionModule

@NgModule(
  imports = @@[BrowserModule, MaterialModule, FlexLayoutModule, AppRoutingModule , HttpModule,
    VenueModule, TeamModule, UserModule, TextModule, GlobalTextModule, ApplicationContextModule, SeasonModule] :+
  InMemoryWebApiModule.forRoot(%%[MockData],InMemoryBackendConfigArgs(delay = 0)),
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
      <div id="sidenav-content" style="padding-left:1em;height:calc(100vh - 72px);" fxLayout="column">
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
