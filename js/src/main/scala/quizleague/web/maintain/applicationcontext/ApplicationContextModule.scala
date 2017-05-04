package quizleague.web.maintain.applicationcontext

import angular.flexlayout.FlexLayoutModule
import angular.material.MaterialModule
import angulate2.common.CommonModule
import angulate2.ext.classModeScala
import angulate2.forms.FormsModule
import angulate2.http.Http
import angulate2.router.{ Route, RouterModule }
import angulate2.std._
import quizleague.web.maintain.ServiceRoot
import quizleague.web.maintain.globaltext.GlobalTextService
import quizleague.web.maintain.user.UserService
import quizleague.web.service.applicationcontext.{ ApplicationContextGetService, ApplicationContextPutService }
import quizleague.web.maintain.season.SeasonService

@NgModule(
  imports = @@[CommonModule, FormsModule, MaterialModule, RouterModule, FlexLayoutModule, ApplicationContextRoutesModule],
  declarations = @@[ApplicationContextComponent],
  providers = @@[ApplicationContextService])
class ApplicationContextModule

@Routes(
  root = false,
  Route(
    path = "applicationContext",
    component = %%[ApplicationContextComponent]))
class ApplicationContextRoutesModule

@Injectable
@classModeScala
class ApplicationContextService(
    override val http: Http, 
    override val userService:UserService, 
    override val globalTextService: GlobalTextService,
    override val seasonService:SeasonService) extends ApplicationContextGetService
  with ApplicationContextPutService
  with ServiceRoot


