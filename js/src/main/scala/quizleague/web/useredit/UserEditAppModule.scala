package quizleague.web.useredit

import quizleague.web.core._

import quizleague.web.site.common._



object UserEditAppModule extends Module {
  
  override val modules = @@(CommonModule)
  
    override val routes = @@(
      RouteConfig(path="/useredit/team/:id", components = Map("default" -> TeamEditPage )),
      RouteConfig(path = "/useredit/:token",components = Map("default" -> TokenCheckComponent))
      )
 
  override val components = @@(UserEditAppComponent)
   
}



