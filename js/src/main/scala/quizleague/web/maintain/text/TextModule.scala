package quizleague.web.maintain.text

import angulate2.std._

import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import quizleague.web.model.Venue
import angulate2.http.Http
import quizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import quizleague.web.util.UUID
import quizleague.web.names.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import rxjs.Observable
import angulate2.router.RouterModule
import quizleague.web.service.text._
import quizleague.web.maintain._

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule, TextRoutesModule],
  declarations = @@[TextComponent,TextEditor],
  providers = @@[TextService]
   
)
class TextModule

@Routes(
  root = false,
  Route(
    path = "maintain/text/:id",
    component = %%[TextComponent]
  )
)
class TextRoutesModule 



@Injectable
@classModeScala
class TextService(override val http:Http) extends TextGetService with TextPutService with ServiceRoot
