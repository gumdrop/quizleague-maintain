package quizleague.web.site.global

import quizleague.web.service.applicationcontext.ApplicationContextGetService
import quizleague.web.site.ServiceRoot
import angulate2.std._
import angulate2.ext._
import angulate2.http.Http
import quizleague.web.site.text.GlobalTextService
import quizleague.web.site.user.UserService


@NgModule(
  providers = @@[ApplicationContextService]       
)
class ApplicationContextModule

@Injectable
@classModeScala
class ApplicationContextService(
  override val http:Http,
  override val globalTextService:GlobalTextService,
  override val userService:UserService
) extends ApplicationContextGetService with ServiceRoot 