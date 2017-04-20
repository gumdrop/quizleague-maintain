package quizleague.web.site.team

import angulate2.ext.classModeScala
import angulate2.std.Component
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.SectionComponent

@Component(
  template = s"""
  <div>
    <h2>Team Detail</h2>
  </div>
  """    
)
@classModeScala
class TeamComponent(override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent