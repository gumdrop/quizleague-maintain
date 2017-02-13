package org.chilternquizleague.web.maintain.applicationcontext

import angulate2.ext.classModeScala
import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import org.chilternquizleague.web.model._
import org.chilternquizleague.domain.{Team => DomTeam}
import angulate2.http.Http
import org.chilternquizleague.web.service.applicationcontext._
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.web.maintain.component.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import org.chilternquizleague.web.maintain.user.UserService
import org.chilternquizleague.web.maintain.globaltext.GlobalTextService


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

trait ApplicationContextNames extends ComponentNames{
  override val typeName = "applicationContext"
}

@Injectable
@classModeScala
class ApplicationContextService(override val http:Http, override val userService:UserService, override val globalTextService:GlobalTextService) extends ApplicationContextGetService with ApplicationContextPutService


