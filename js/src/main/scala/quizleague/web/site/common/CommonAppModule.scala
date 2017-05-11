package quizleague.web.site.common

import angulate2.std._
import scalajs.js
import angular.material.MaterialModule

@NgModule(
  imports = @@[MaterialModule],
  declarations = @@[SectionTitleComponent],
  providers = @@[TitleService],
  exports = @@[SectionTitleComponent]
)
class CommonAppModule