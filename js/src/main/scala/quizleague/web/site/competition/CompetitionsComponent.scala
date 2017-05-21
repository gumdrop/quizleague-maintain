package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.std.Component
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.SectionComponent
import quizleague.web.site.common.TitleService
import quizleague.web.site.common.TitledComponent
import quizleague.web.site.global.ApplicationContextService

@Component(
  template = """
    <ql-named-text name="competitions-front-page"></ql-named-text>
  """
)
@classModeScala
class CompetitionsComponent(
    override val sideMenuService: SideMenuService,
    override val titleService: TitleService) extends SectionComponent with MenuComponent with TitledComponent {

  setTitle("Competitions")
}

@Component(
  template = """
  <ql-section-title>
     <span>Competitions</span><ql-season-select [currentSeason]="season"></ql-season-select>
  </ql-section-title>
  """
)
class CompetitionsTitleComponent(
    viewService: CompetitionViewService) {
  val season = viewService.season

}