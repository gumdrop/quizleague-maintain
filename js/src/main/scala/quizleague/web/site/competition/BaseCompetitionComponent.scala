package quizleague.web.site.competition

import angulate2.router.ActivatedRoute
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.common.TitleService
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.SectionComponent
import quizleague.web.site.common.TitledComponent
import scala.scalajs.js.annotation.JSExport
import quizleague.web.site.common.MenuComponent
import quizleague.web.site.common.ComponentUtils

abstract class BaseCompetitionComponent( 
  route: ActivatedRoute,
  service: CompetitionService,
  viewService: CompetitionViewService,
  applicationContextService: ApplicationContextService,
  override val titleService: TitleService,
  override val sideMenuService: SideMenuService)
    extends SectionComponent
    with MenuComponent
    with TitledComponent 
    with ComponentUtils{
  
  
  @JSExport
  val itemObs = route.params.switchMap((params, i) => service.get(params("id")))

  itemObs.subscribe(t => setTitle(t.name)) 
  
}