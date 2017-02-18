package quizleague.web.maintain.venue

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
import quizleague.web.maintain.component.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import rxjs.Observable
import quizleague.web.service.venue.VenuePutService
import quizleague.web.maintain._

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule,VenueRoutesModule],
  declarations = @@[VenueComponent,VenueListComponent],
  providers = @@[VenueService]
   
)
class VenueModule

@Routes(
  root = false,
  Route(
    path = "venue/:id",
    component = %%[VenueComponent]
  ),
  Route(
    path = "venue",
    component = %%[VenueListComponent]
  )
)
class VenueRoutesModule 


trait VenueNames extends ComponentNames{
  override val typeName = "venue"
}

@Injectable
@classModeScala
class VenueService(override val http: Http) extends VenuePutService with ServiceRoot

