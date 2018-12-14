package quizleague.web.useredit

import quizleague.web.core._

import quizleague.web.site.common._



object UserEditAppModule extends Module {
  
  override val modules = @@(CommonModule)
  
    override val routes = @@(
      RouteConfig(path = "/useredit/login",components = Map("default" -> LoginCheckComponent)),
      RouteConfig(path="/useredit/team/:id", components = Map("default" -> TeamEditPage )),
      //RouteConfig(path = "/useredit/index.html",redirect = "/useredit/login")
      )
 
  override val components = @@(UserEditAppComponent)
   
}



