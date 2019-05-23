package quizleague.web.maintain.user


import scalajs.js
import quizleague.web.service.user._
import quizleague.web.util.UUID
import quizleague.web.names.ComponentNames
import quizleague.web.maintain._
import quizleague.web.core._
import com.felstar.scalajs.vue._
import quizleague.web.model.User


object UserModule extends Module{
  override val routes = @@(
      RouteConfig(path = "/maintain/user", components = Map("default" -> UserListComponent)),
      RouteConfig(path="/maintain/user/:id", components = Map("default" -> UserComponent))
       )
      
}


object UserService extends UserGetService with UserPutService{
  def userExists(user:User) = query(db.collection(uriRoot).where("email","=",user.email)).map(!_.isEmpty)
  def userForEmail(email:String) = query(db.collection(uriRoot).where("email","==",email)).map(_.headOption)
}

object SiteUserService extends SiteUserGetService with SiteUserPutService{
  val userService = UserService
}

