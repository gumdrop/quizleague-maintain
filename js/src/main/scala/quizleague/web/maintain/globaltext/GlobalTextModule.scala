package quizleague.web.maintain.globaltext

import quizleague.web.service.globaltext.GlobalTextGetService
import quizleague.web.service.globaltext.GlobalTextPutService
import quizleague.web.maintain.text.TextService
import quizleague.web.core._
import quizleague.web.maintain.MaintainMenuComponent
import quizleague.web.core.RouteConfig

object GlobalTextModule extends Module {
    override val routes = @@(
      RouteConfig(path = "/maintain/globaltext", components = Map("default" -> GlobalTextListComponent)),
      RouteConfig(path="/maintain/globaltext/:id", components = Map("default" -> GlobalTextComponent))
       )

}

object GlobalTextService extends GlobalTextGetService with GlobalTextPutService{
  override val textService = TextService
}