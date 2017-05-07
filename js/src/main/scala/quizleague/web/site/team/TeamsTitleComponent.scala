package quizleague.web.site.team

import angulate2.ext.classModeScala
import angulate2.std.Component
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.SectionComponent
import angulate2.router.ActivatedRoute
import rxjs.Observable
import quizleague.web.model.Team
import angulate2.core.OnInit

@Component(
  template = s"""
  <ql-section-title>
     <span>Teams</span>
  </ql-section-title>
  """    
)
@classModeScala
class TeamsTitleComponent