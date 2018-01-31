package quizleague.web.maintain.venue


import scalajs.js

import quizleague.web.service.venue._

import quizleague.web.util.UUID

import quizleague.web.maintain._
import quizleague.web.core._
import com.felstar.scalajs.vue._


object VenueModule extends Module{
  override val routes = @@(
      RouteConfig(path = "/maintain/venue", components = Map("default" -> VenueListComponent)),
      RouteConfig(path="/maintain/venue/:id", components = Map("default" -> VenueComponent))
       )
      
}


object VenueService extends VenueGetService with VenuePutService

