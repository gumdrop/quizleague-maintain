package quizleague.web.site.text

import quizleague.web.service.text._
import quizleague.web.core._
import quizleague.web.service.globaltext.GlobalTextGetService


object TextModule extends Module{
  
  
  override val components = @@(TextComponent, NamedTextComponent)
  
   
   
}


object TextService extends TextGetService

object GlobalTextService extends GlobalTextGetService{
  override val textService = TextService
}
