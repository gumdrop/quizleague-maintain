package quizleague.web.site.home

import quizleague.web.core._
import quizleague.web.core.RouteConfig
import scalajs.js

object HomeModule extends Module{
  
  
  override val routes = @@(RouteConfig(path = "/home", components = Map(
      "default" -> HomeComponent)))
      
   
}