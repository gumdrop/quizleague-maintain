package org.chilternquizleague.web.maintain.user

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import org.chilternquizleague.web.model.Venue
import angulate2.http.Http
import org.chilternquizleague.web.service.user._
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.web.util.UUID
import org.chilternquizleague.web.maintain.component.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import rxjs.Observable
import angulate2.router.RouterModule
import org.chilternquizleague.web.maintain._

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule, UserRoutesModule],
  declarations = @@[UserComponent,UserListComponent],
  providers = @@[UserService]
   
)
class UserModule

@Routes(
  root = false,
  Route(
    path = "user/:id",
    component = %%[UserComponent]
  ),
  Route(
    path = "user",
    component = %%[UserListComponent]
  )
)
class UserRoutesModule 


trait UserNames extends ComponentNames{
  override val typeName = "user"
}

@Injectable
@classModeScala
class UserService(override val http:Http) extends UserGetService with UserPutService with ServiceRoot

