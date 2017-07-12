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
import quizleague.web.names.ComponentNames

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
    path = "maintain/venue",
    children = @@@(
      Route(path = "", children = @@@(
        Route(path = ":id", component = %%[VenueComponent]),
        Route(path = "", component = %%[VenueListComponent]))),
      Route(path = "", component = %%[MaintainMenuComponent], outlet = "sidemenu"))))
class VenueRoutesModule 


@Injectable
@classModeScala
class VenueService(override val http: Http) extends VenuePutService with ServiceRoot

