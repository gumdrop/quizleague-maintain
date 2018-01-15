package quizleague.web.maintain.user


import scalajs.js

import quizleague.web.service.user._

import quizleague.web.util.UUID
import quizleague.web.names.ComponentNames



import quizleague.web.maintain._
import quizleague.web.core._
import com.felstar.scalajs.vue._


object UserModule extends Module{
  override val routes = @@(
      RouteConfig(path = "/maintain/user", components = Map("default" -> UserListComponent, "sidenav" -> MaintainMenuComponent)),
      RouteConfig(path="/maintain/user/:id", components = Map("default" -> UserComponent, "sidenav" -> MaintainMenuComponent))
       )
      
}


object UserService extends UserGetService with UserPutService

