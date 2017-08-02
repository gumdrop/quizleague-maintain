package quizleague.web.site.global

import quizleague.web.service.applicationcontext.ApplicationContextGetService
import quizleague.web.site.ServiceRoot
import angulate2.std._
import angulate2.ext._
import angulate2.http.Http
import quizleague.web.site.text.GlobalTextService
import quizleague.web.site.user.UserService
import quizleague.web.site.common._
import quizleague.web.site.season.SeasonService
import quizleague.web.service.CachingService
import quizleague.web.model.ApplicationContext

@NgModule(
  providers = @@[ApplicationContextService]       
)
@classModeScala
class ApplicationContextModule

@Injectable
@classModeScala
class ApplicationContextService (
  override val http:Http,
  override val globalTextService:GlobalTextService,
  override val userService:UserService,
  override val seasonService:SeasonService
) extends ApplicationContextGetService with ServiceRoot with CachingService[ApplicationContext]