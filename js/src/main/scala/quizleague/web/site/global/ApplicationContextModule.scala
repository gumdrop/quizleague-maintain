package quizleague.web.site.global

import quizleague.web.service.applicationcontext.ApplicationContextGetService
import quizleague.web.site.ServiceRoot
import angulate2.std._
import angulate2.ext._
import angulate2.http.Http
import quizleague.web.site.text.GlobalTextService
import quizleague.web.site.user.UserService
import quizleague.web.site.common._

@NgModule(
  providers = @@[ApplicationContextService]       
)
@classModeScala
class ApplicationContextModule(override val sideMenuService:SideMenuService) extends SectionModule with NoMenuModule{
  onInit()
}

@Injectable
@classModeScala
class ApplicationContextService (
  override val http:Http,
  override val globalTextService:GlobalTextService,
  override val userService:UserService
) extends ApplicationContextGetService with ServiceRoot 