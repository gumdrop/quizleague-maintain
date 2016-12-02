package org.chilternquizleague.maintain.venue


import angulate2.std._
import angularmaterial.MaterialModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

object routes{
  val routes = js.Array(
     Route(
        path = "venue/:id",
        component = %%[VenueComponent]
      )
  )
}


@NgModule(
  imports = @@[VenuesModule] :+ RouterModule.forRoot(routes.routes),
  exports = @@[RouterModule]
)
class VenueRoutesModule {
  
}