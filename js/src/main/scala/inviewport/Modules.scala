package inviewport

import angulate2.std._


@NgModule(
 declarations = @@[InViewport, InViewportScrollWatcher],
 exports = @@[InViewport,InViewportScrollWatcher],
 providers = @@[InViewportService]
)
class InViewportModule

