package quizleague.web.site.competition

import angulate2.router.ActivatedRoute
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.common.TitleService
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.SectionComponent
import quizleague.web.site.common.TitledComponent
import scala.scalajs.js.annotation.JSExport
import quizleague.web.site.common.MenuComponent

abstract class BaseCompetitionComponent( 
  route: ActivatedRoute,
  service: CompetitionService,
  viewService: CompetitionViewService,
  applicationContextService: ApplicationContextService,
  override val titleService: TitleService,
  override val sideMenuService: SideMenuService)
    extends SectionComponent
    with MenuComponent
    with TitledComponent {
  
  @JSExport
  val textName:String
  
  @JSExport
  val itemObs = route.params.switchMap((params, i) => service.get(params("id"))(4))

  itemObs.subscribe(t => setTitle(t.name)) 
  
}