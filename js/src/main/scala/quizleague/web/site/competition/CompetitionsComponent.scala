package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.std.Component
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.SectionComponent
import quizleague.web.site.common.TitleService
import quizleague.web.site.common.TitledComponent

@Component(
  template = s"""
  <div>
    <h2>Competitions Home</h2>
  </div>
  """    
)
@classModeScala
class CompetitionsComponent(
    override val sideMenuService:SideMenuService,
    override val titleService:TitleService
    ) extends SectionComponent with MenuComponent with TitledComponent{
  
  setTitle("Competitions")
}

@Component(
  template = s"""
  <ql-section-title>
     <span>Competitions</span>
  </ql-section-title>
  """    
)
class CompetitionsTitleComponent(
  viewService:CompetitionViewService    
){
  val service = viewService.service
}