package quizleague.web.maintain

import quizleague.web.core._
import quizleague.web.core.RouteConfig
import quizleague.web.maintain.user.UserModule
import quizleague.web.maintain.venue.VenueModule
import quizleague.web.maintain.text.TextModule
import quizleague.web.maintain.team.TeamModule
import quizleague.web.maintain.applicationcontext.ApplicationContextModule
import quizleague.web.maintain.globaltext.GlobalTextModule
import quizleague.web.maintain.season.SeasonModule

object MaintainModule extends Module{
  
  override val modules = @@(UserModule, VenueModule, TextModule, TeamModule, ApplicationContextModule, GlobalTextModule, SeasonModule)
  
  override val routes = @@(
    RouteConfig(path = "/maintain", components = Map("sidenav"->MaintainMenuComponent))    
  )
}
