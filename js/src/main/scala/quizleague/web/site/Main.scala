package quizleague.web.site

import angulate2.std._
import angulate2.router.{Route, RouterModule}
import angulate2.platformBrowser.BrowserModule
import angular.material.MaterialModule
import angulate2.forms.FormsModule

import scala.scalajs.js
import angular.flexlayout.FlexLayoutModule
import angular.flexlayout.FlexLayoutModule
import angulate2.ext.inMemoryWebApi.{InMemoryWebApiModule,InMemoryBackendConfigArgs}
import angulate2.http.HttpModule
import quizleague.web.mock.MockData
import quizleague.web.site.text.NamedTextComponent
import quizleague.web.site.global.ApplicationContextModule
import quizleague.web.site.text.TextModule
import quizleague.web.site.user.UserModule
import quizleague.web.site.text.NamedTextComponent
import quizleague.web.site.global.ApplicationContextService


@NgModule(
  imports = @@[BrowserModule, MaterialModule, FlexLayoutModule, AppRoutingModule , HttpModule, ApplicationModules, TextModule ] :+
  InMemoryWebApiModule.forRoot(%%[MockData],InMemoryBackendConfigArgs(delay = 0)),
  declarations = @@[AppComponent,RootComponent,RootMenuComponent], 
  bootstrap = @@[AppComponent]
)
class AppModule 


@NgModule(
  imports = @@[ApplicationContextModule, UserModule]
)
class ApplicationModules

@Routes(
  root = true,
  Route(path = "", component = %%[RootComponent]),
  Route(path = "", component = %%[RootMenuComponent], outlet="sidemenu")
)
class AppRoutingModule

@Component( 
  selector = "ql-app",
  template = """
  <div>
   <md-toolbar color='primary'>
      <span class="toolbar-medium">
        <button md-icon-button (click)="sidenav.toggle()">
          <md-icon class="md-24">menu</md-icon>
        </button>
        <span>{{leagueName}}</span>
      </span>
      <md-toolbar-row class="toolbar-medium">
        <div fxLayout="row">
          <a routerLink="/" md-button >Home</a>
          <a routerLink="/team" md-button >Teams</a>
          <a routerLink="/competition" md-button >Competitions</a>
          <a routerLink="/results" md-button >Results</a>
          <a routerLink="/venue" md-button >Venues</a>
        </div>
      </md-toolbar-row>
    </md-toolbar>
    <md-sidenav-container>
      <md-sidenav #sidenav mode="side" [opened]="showSidenav">
        <router-outlet name="sidemenu"></router-outlet>
      </md-sidenav>
      <div id="sidenav-content" style="padding-left:1em;height:calc(100vh - 128px);" fxLayout="column">
        <router-outlet></router-outlet>
      </div>
    </md-sidenav-container>
  </div>
  """
)
class AppComponent(service:ApplicationContextService) extends OnInit {
  
  var leagueName:String = _
  
  var showSidenav = false
  
  override def ngOnInit() = service.get.subscribe(appc => {leagueName = appc.leagueName})
}

@Component(
  template = """
  <div fxLayout="row">
    <div>League Tables here</div>
    <md-card>
      <ql-named-text name="front_page_main"></ql-named-text>
    </md-card>
  </div>
  """
)
class RootComponent

@Component(
  template = ""
)
class RootMenuComponent
