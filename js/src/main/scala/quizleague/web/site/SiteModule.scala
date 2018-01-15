package quizleague.web.site

import com.felstar.scalajs.vue._
import scalajs.js.Dynamic.literal
import scalajs.js
import quizleague.web.site.home.HomeModule
import quizleague.web.core._
import quizleague.web.site.team.TeamModule

import quizleague.web.store.Firestore
import io.circe._,io.circe.parser._,io.circe.syntax._,io.circe.scalajs.convertJsToJson
import quizleague.domain.ApplicationContext
import quizleague.util.json.codecs.DomainCodecs._
import rxscalajs.subjects.BehaviorSubject
import quizleague.domain.ApplicationContext
import rxscalajs.Subject
import rxscalajs.subjects.ReplaySubject
import quizleague.web.site.text.TextModule
import quizleague.web.site.venue.VenueModule
import quizleague.web.site.fixtures.FixturesModule
import quizleague.web.site.leaguetable.LeagueTableModule
import quizleague.web.service.applicationcontext.ApplicationContextGetService
import quizleague.web.service.globaltext.GlobalTextGetService
import quizleague.web.site.text.TextService
import quizleague.web.site.season._
import quizleague.web.site.user.UserService
import quizleague.web.site.text.GlobalTextService
import quizleague.web.site.results.ResultsModule
import quizleague.web.site.competition.CompetitionModule
import quizleague.web.site.calendar.CalendarModule
import quizleague.web.site.other._
import quizleague.web.maintain.MaintainModule



object SiteModule extends Module {
  
  override val modules = @@(HomeModule, TeamModule, TextModule, VenueModule, FixturesModule, ResultsModule, LeagueTableModule, CompetitionModule, SeasonModule, CalendarModule, MaintainModule)
  
  override val routes = @@(
      RouteConfig(path="/links", components = Map("default" -> LinksComponent)),
      RouteConfig(path="/rules", components = Map("default" -> RulesComponent)),
      RouteConfig(path = "",redirect = "/home")
      )
  
  override val components = @@(SiteComponent, SideMenuHeader)
   
}



object ApplicationContextService extends ApplicationContextGetService{
  
  override val globalTextService = GlobalTextService
  override val seasonService = SeasonService
  override val userService = UserService
  
}

object SiteService {
  val sidemenu = BehaviorSubject[Boolean](false)
}