package quizleague.web.maintain.applicationcontext


import scalajs.js

import quizleague.web.service.team._

import quizleague.web.maintain._
import quizleague.web.core._
import com.felstar.scalajs.vue._
import quizleague.web.maintain.user.UserService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.maintain.text.TextService
import quizleague.web.service.applicationcontext._
import quizleague.web.maintain.globaltext.GlobalTextService
import quizleague.web.maintain.season.SeasonService



object ApplicationContextModule extends Module{
  override val routes = @@(
      RouteConfig(path = "/maintain/applicationcontext", components = Map("default" -> ApplicationContextComponent))
       )
      
}


object ApplicationContextService extends ApplicationContextGetService with ApplicationContextPutService{

  override val userService = UserService
  override val globalTextService = GlobalTextService
  override val seasonService = SeasonService
  

}

