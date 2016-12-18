package org.chilternquizleague.maintain.team


import angulate2.std._
import angular.material.MaterialModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

object routes{
  val routes = js.Array(
      Route(
        path = "team/:id",
        component = %%[TeamComponent]
      ),
      Route(
        path = "team",
        component = %%[TeamListComponent]
      )
  )
}


@NgModule(
  imports = @@[TeamModule] :+ RouterModule.forRoot(routes.routes),
  exports = @@[RouterModule]
)
class TeamRoutesModule 