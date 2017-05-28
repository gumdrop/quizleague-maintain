package quizleague.web.site

import scala.scalajs.js

import angular.core.BrowserAnimationsModule
import angular.flexlayout.FlexLayoutModule
import angular.material.MaterialModule
import angulate2.ext.classModeScala
import angulate2.ext.inMemoryWebApi.{ InMemoryBackendConfigArgs, InMemoryWebApiModule }
import angulate2.http.HttpModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{ Route, Router }
import angulate2.std._
import quizleague.web.mock.MockData
import quizleague.web.site.common.{ CommonAppModule, NoMenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.site.competition.CompetitionModule
import quizleague.web.site.fixtures.FixturesModule
import quizleague.web.site.global.{ ApplicationContextModule, ApplicationContextService }
import quizleague.web.site.leaguetable.LeagueTableModule
import quizleague.web.site.results.ResultsModule
import quizleague.web.site.season.SeasonModule
import quizleague.web.site.team.TeamModule
import quizleague.web.site.text.TextModule
import quizleague.web.site.user.UserModule
import quizleague.web.site.venue.VenueModule
import quizleague.web.util.Logging
import rxjs.Observable
import quizleague.web.site.season.SeasonService
import angulate2.common.CommonModule
import quizleague.web.site.root._
import quizleague.web.site.leaguetable.LeagueTableModule
import quizleague.web.site.season.SeasonService
import quizleague.web.site.results.ResultsComponentsModule
import quizleague.web.site.fixtures.FixturesComponentsModule
import java.time.LocalDate
import quizleague.web.site.calendar.CalendarModule




@NgModule(
  imports = @@[BrowserModule,BrowserAnimationsModule ,MaterialModule, FlexLayoutModule, HttpModule, RootModule, ApplicationModules, AppRoutingModule] :+
  InMemoryWebApiModule.forRoot(%%[MockData],InMemoryBackendConfigArgs(delay = 500)),
  declarations = @@[AppComponent], 
  bootstrap = @@[AppComponent],
  providers = @@[SideMenuService]
)
@classModeScala
class AppModule (router:Router) extends Logging{
  log(router.config, "Routes : ")
}


@NgModule(
  imports = @@[CommonAppModule, ApplicationContextModule, UserModule, VenueModule, TeamModule, SeasonModule, ResultsModule, FixturesModule, CompetitionModule, CalendarModule]
 )
class ApplicationModules

@Routes(
  root = true,
  Route(path = "home", 
      children = @@@(
          Route(path="", component = %%[RootComponent]), 
          Route(path="", component = %%[RootMenuComponent], outlet="sidemenu"))),
  Route(path = "",   redirectTo = "/home", pathMatch = "full" ))
class AppRoutingModule

@Component( 
  selector = "ql-app",
  template = """
  <div>
   <md-toolbar color='primary' class="mat-elevation-z6">
        <span>
          <button md-icon-button (click)="sidenav.toggle()" [disabled]="!(showSidenav | async)">
            <md-icon class="md-24">menu</md-icon>
          </button>
          <span>{{(context | async)?.leagueName}}</span>
        </span>
        <span style="flex:1 1 0;"></span>
        <span fxHide fxShow.xs="true">
          <button md-icon-button [mdMenuTriggerFor]="appMenu">
            <md-icon class="md-24">more_vert</md-icon>
          </button>
        </span>
      <md-toolbar-row fxShow fxHide.xs="true">
         <div *ngTemplateOutlet="menu"></div>
      </md-toolbar-row>
    </md-toolbar>
    <md-sidenav-container>
      <md-sidenav #sidenav [mode]="menuMode()" [opened]="showByDefault() && (showSidenav | async)" (click)="!showByDefault() && sidenav.toggle()">
        <router-outlet name="sidemenu"></router-outlet>
      </md-sidenav>
      <div id="sidenav-content" style="padding-left:1em;height:calc(100vh - 128px);" fxLayout="column">
        <div fxLayout="column" fxLayoutGap="5px">
          <router-outlet name="title"></router-outlet>
          <router-outlet></router-outlet>
        </div>
      </div>
    </md-sidenav-container>
  </div>
  <md-menu #appMenu="mdMenu">
     <div *ngTemplateOutlet="menu"></div>
  </md-menu>
  <ng-template #menu>
     <div fxLayout="row" fxLayout.xs="column">
        <a routerLink="/home" md-button routerLinkActive="active">Home</a>
        <a routerLink="/team" md-button routerLinkActive="active">Teams</a>
        <a routerLink="/competition" md-button routerLinkActive="active">Competitions</a>
        <a routerLink="/results" md-button routerLinkActive="active">Results</a>
        <a routerLink="/venue" md-button routerLinkActive="active">Venues</a>
        <a routerLink="/calendar" md-button routerLinkActive="active">Calendar</a>
     </div>
  </ng-template>
  """,
  styles = js.Array("""
    md-sidenav-container{
      margin-top:8px;
    }
  """,
  """[md-icon-button]{top:-3px;}""")
)
class AppComponent(
    service:ApplicationContextService, 
    sideMenuService:SideMenuService){
  
  val context = service.get
  
  val showSidenav = sideMenuService.showMenu 
  
  def menuMode() = sideMenuService.menuMode
  
  def showByDefault() = sideMenuService.showByDefault
  
}


