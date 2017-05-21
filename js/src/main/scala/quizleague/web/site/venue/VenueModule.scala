package quizleague.web.site.venue

import angulate2.std._
import angular.material.MaterialModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{ Route, RouterModule }

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
import quizleague.web.service.venue.VenueGetService
import quizleague.web.site._
import quizleague.web.site.common.CommonAppModule
import quizleague.web.site.text.TextModule

@NgModule(
  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, VenueRoutesModule, CommonAppModule, TextModule],
  declarations = @@[VenueComponent, VenueTitleComponent, VenueMenuComponent, VenuesComponent, VenuesTitleComponent],
  providers = @@[VenueService])
class VenueModule

@Routes(
  root = false,
  Route(
    path = "venue",
    children = @@@(
      Route(path = ":id", children = @@@(
        Route(path = "", children = @@@(
          Route(path = "", component = %%[VenueComponent]),
          Route(path = "", component = %%[VenueTitleComponent], outlet = "title"))))),
      Route("", children = @@@(
        Route(path = "", component = %%[VenuesComponent]),
        Route(path = "", component = %%[VenuesTitleComponent], outlet = "title"))),
      Route(path = "", component = %%[VenueMenuComponent], outlet = "sidemenu"))))
class VenueRoutesModule

@Injectable
@classModeScala
class VenueService(override val http: Http) extends VenueGetService with ServiceRoot

