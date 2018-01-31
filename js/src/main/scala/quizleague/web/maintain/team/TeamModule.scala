package quizleague.web.maintain.team


import scalajs.js

import quizleague.web.service.team._

import quizleague.web.maintain._
import quizleague.web.core._
import com.felstar.scalajs.vue._
import quizleague.web.maintain.user.UserService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.maintain.text.TextService


object TeamModule extends Module{
  override val routes = @@(
      RouteConfig(path = "/maintain/team", components = Map("default" -> TeamListComponent)),
      RouteConfig(path="/maintain/team/:id", components = Map("default" -> TeamComponent))
       )
      
}


object TeamService extends TeamGetService with TeamPutService{
  override val textService = TextService
  override val userService = UserService
  override val venueService = VenueService
  

}

