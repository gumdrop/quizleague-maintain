package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std._
import quizleague.web.site.common.{ MenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.site.global.ApplicationContextService

@Component(
  template = TemplateConstants.cupTemplate
)
@classModeScala
class CupCompetitionComponent(
  route: ActivatedRoute,
  service: CompetitionService,
  viewService: CompetitionViewService,
  applicationContextService: ApplicationContextService,
  titleService: TitleService,
  sideMenuService: SideMenuService)
    extends BaseCompetitionComponent(
      route,
      service,
      viewService,
      applicationContextService,
      titleService,
      sideMenuService
    )
    with TeamCompetitionComponent{
  
    override val textName:String = "cup-comp"
}

   
