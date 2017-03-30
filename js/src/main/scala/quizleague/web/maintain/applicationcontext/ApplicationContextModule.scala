package quizleague.web.maintain.applicationcontext

import angulate2.ext.classModeScala
import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import quizleague.web.model._
import quizleague.domain.{Team => DomTeam}
import angulate2.http.Http
import quizleague.web.service.applicationcontext._
import angular.flexlayout.FlexLayoutModule
import quizleague.web.names.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import quizleague.web.maintain._
import quizleague.web.maintain.user.UserService
import quizleague.web.maintain.globaltext.GlobalTextService


@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule,ApplicationContextRoutesModule],
  declarations = @@[ApplicationContextComponent],
  providers = @@[ApplicationContextService]
   
)
class ApplicationContextModule

@Routes(
  root = false,
      Route(
        path = "applicationContext",
        component = %%[ApplicationContextComponent]
      )
)
class ApplicationContextRoutesModule 



@Injectable
@classModeScala
class ApplicationContextService(override val http:Http, override val userService:UserService, override val globalTextService:GlobalTextService) extends ApplicationContextGetService 
with ApplicationContextPutService
with ServiceRoot


