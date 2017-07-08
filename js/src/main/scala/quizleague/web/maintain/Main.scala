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
import quizleague.web.mock.MockData
import quizleague.web.maintain.text.TextModule
import quizleague.web.maintain.globaltext.GlobalTextModule
import quizleague.web.maintain.applicationcontext.ApplicationContextModule
import quizleague.web.maintain.season.SeasonModule
import quizleague.web.maintain.competition.CompetitionModule
import angular.core.BrowserAnimationsModule
import quizleague.web.site.common.SectionComponent
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.SideMenuService
import angulate2.ext.classModeScala

@NgModule(
  imports = @@[MaterialModule, FlexLayoutModule, MaintainRoutingModule , HttpModule, BrowserAnimationsModule, DepsModule],
  //InMemoryWebApiModule.forRoot(%%[MockData],InMemoryBackendConfigArgs(delay = 0)),
  declarations = @@[MaintainMenuComponent,RootComponent]
)
class MaintainModule 

@NgModule(
  imports = @@[VenueModule, TeamModule, UserModule, TextModule, GlobalTextModule, ApplicationContextModule, SeasonModule]
)
class DepsModule

@Routes(
  root = false,
  Route(
    path = "maintain",
    children = @@@(
      Route(path = "", children = @@@(
        Route(path = "", component = %%[RootComponent]))),
      Route(path = "", component = %%[MaintainMenuComponent], outlet = "sidemenu"))))
class MaintainRoutingModule

@Component(
  template = """
        <div  fxLayout="column">
          <a routerLink="/maintain/applicationContext" md-button routerLinkActive="active">Application Context</a>
          <a routerLink="/maintain/globalText" md-button routerLinkActive="active">Global Text</a>
          <a routerLink="/maintain/team" md-button routerLinkActive="active">Teams</a>
          <a routerLink="/maintain/season" md-button routerLinkActive="active">Seasons</a>
          <a routerLink="/maintain/user" md-button routerLinkActive="active">Users</a>
          <a routerLink="/maintain/venue" md-button routerLinkActive="active">Venues</a>
        </div>
  """
)
class MaintainMenuComponent

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
@classModeScala
class RootComponent(override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent 
