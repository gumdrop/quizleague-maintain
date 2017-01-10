package org.chilternquizleague.maintain.venue


import angulate2.std._
import angular.material.MaterialModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

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