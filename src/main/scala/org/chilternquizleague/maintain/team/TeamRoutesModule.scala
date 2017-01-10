package org.chilternquizleague.maintain.team


import angulate2.std._
import angular.material.MaterialModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

@Routes(
  root = false,
       Route(
        path = "team/:id",
        component = %%[TeamComponent]
      ),
      Route(
        path = "team",
        component = %%[TeamListComponent]
      )
)
class TeamRoutesModule 