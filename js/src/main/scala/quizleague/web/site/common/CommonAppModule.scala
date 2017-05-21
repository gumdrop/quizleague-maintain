package quizleague.web.site.common

import angulate2.std._
import scalajs.js
import angular.material.MaterialModule

@NgModule(
  imports = @@[MaterialModule],
  declarations = @@[SectionTitleComponent,LineBreakPipe],
  providers = @@[TitleService, LineBreakPipe],
  exports = @@[SectionTitleComponent,LineBreakPipe]
)
class CommonAppModule