package quizleague.web.site.common

import angulate2.std._

@Component(
  selector="ql-section-title",
  template = s"""
  <md-toolbar color="primary">
     <ng-content></ng-content>
  </md-toolbar>
  """    
)
class SectionTitleComponent