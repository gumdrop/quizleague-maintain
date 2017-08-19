package angular.nginviewport


import scalajs.js
import scala.scalajs.js.annotation.JSImport
import angulate2.std._
import nginviewport.InViewport
import nginviewport.InViewport
import nginviewport.InViewportService
import nginviewport.InViewportScrollWatcher

@NgModule(
 declarations = @@[InViewport, InViewportScrollWatcher],
 exports = @@[InViewport,InViewportScrollWatcher],
 providers = @@[InViewportService]
)
class InViewportModule

