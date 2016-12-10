package org.chilternquizleague.maintain.venue


import angulate2.std._
import angular.material.MaterialModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

object routes{
  val routes = js.Array(
      Route(
        path = "venue/:id",
        component = %%[VenueComponent]
      ),
      Route(
        path = "venue",
        component = %%[VenueListComponent]
      )
  )
}


@NgModule(
  imports = @@[VenueModule] :+ RouterModule.forRoot(routes.routes),
  exports = @@[RouterModule]
)
class VenueRoutesModule 