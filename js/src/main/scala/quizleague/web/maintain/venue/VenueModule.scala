package quizleague.web.maintain.venue


import scalajs.js

import quizleague.web.service.venue._

import quizleague.web.util.UUID

import quizleague.web.maintain._
import quizleague.web.core._
import com.felstar.scalajs.vue._


object VenueModule extends Module{
  override val routes = @@(
      RouteConfig(path = "/maintain/venue", components = Map("default" -> VenueListComponent, "sidenav" -> MaintainMenuComponent)),
      RouteConfig(path="/maintain/venue/:id", components = Map("default" -> VenueComponent, "sidenav" -> MaintainMenuComponent))
       )
      
}


object VenueService extends VenueGetService with VenuePutService

