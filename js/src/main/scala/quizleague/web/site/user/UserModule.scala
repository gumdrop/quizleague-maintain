package quizleague.web.site.user

import quizleague.web.service.user.UserGetService
import quizleague.web.site.ServiceRoot
import angulate2.http.Http
import angulate2.std._
import angulate2.ext._



@NgModule(
  providers = @@[UserService] 
)
class UserModule

@Injectable
@classModeScala
class UserService(override val http:Http) extends UserGetService with ServiceRoot