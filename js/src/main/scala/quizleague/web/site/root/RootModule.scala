package quizleague.web.site.root

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
import quizleague.web.site.leaguetable.LeagueTableModule
import quizleague.web.site.season.SeasonService
import quizleague.web.site.results.ResultsComponentsModule
import quizleague.web.site.fixtures.FixturesComponentsModule
import java.time.LocalDate

@NgModule(
  imports = @@[MaterialModule, FlexLayoutModule, CommonModule,TextModule, LeagueTableModule, ResultsComponentsModule, FixturesComponentsModule],
  declarations = @@[RootComponent, RootMenuComponent],
  exports = @@[RootComponent, RootMenuComponent]
)
class RootModule

