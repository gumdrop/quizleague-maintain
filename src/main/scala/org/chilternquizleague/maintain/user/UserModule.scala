package org.chilternquizleague.maintain.user

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import org.chilternquizleague.maintain.model.Venue
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.component.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import rxjs.Observable
import angulate2.router.RouterModule

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

