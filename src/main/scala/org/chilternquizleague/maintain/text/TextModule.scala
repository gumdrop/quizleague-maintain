package org.chilternquizleague.maintain.text

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
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule, TextRoutesModule],
  declarations = @@[TextComponent],
  providers = @@[TextService]
   
)
class TextModule

@Routes(
  root = false,
  Route(
    path = "text/:id",
    component = %%[TextComponent]
  )
)
class TextRoutesModule 


trait TextNames extends ComponentNames{
  override val typeName = "text"
}

