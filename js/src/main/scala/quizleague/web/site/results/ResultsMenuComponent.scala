package quizleague.web.site.results

import angulate2.ext.classModeScala
import angulate2.std.Component
import angulate2.core.OnInit
import quizleague.web.model.Team
import rxjs.Observable
import scalajs.js

@Component(
  template = s"""
  <div fxLayout="column">
   <a fxFlexAlign="start" routerLink="/results/all"  md-menu-item routerLinkActive="active" >All Results</a>
   <a fxFlexAlign="start" routerLink="/results/fixtures"  md-menu-item routerLinkActive="active" >All Fixtures</a>
  </div>
  """    
)
@classModeScala
class ResultsMenuComponent