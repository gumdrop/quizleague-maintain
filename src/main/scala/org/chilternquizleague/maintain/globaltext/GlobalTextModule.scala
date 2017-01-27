package org.chilternquizleague.maintain.globaltext

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
import org.chilternquizleague.maintain.component.IdStuff
import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import rxjs.Observable
import angulate2.router.RouterModule

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule, GlobalTextRoutesModule],
  declarations = @@[GlobalTextComponent,GlobalTextListComponent],
  providers = @@[GlobalTextService]
   
)
class GlobalTextModule

@Routes(
  root = false,
  Route(
    path = "user/:id",
    component = %%[GlobalTextComponent]
  ),
  Route(
    path = "user",
    component = %%[GlobalTextListComponent]
  )
)
class GlobalTextRoutesModule 


trait GlobalTextNames extends ComponentNames{
  override val typeName = "globalText"
}

