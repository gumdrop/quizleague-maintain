package quizleague.web.site.chat

import quizleague.web.core.{@@, Module}
import quizleague.web.service.chat.{ChatGetService, ChatMessageGetService, ChatMessagePutService, ChatPutService}
import quizleague.web.service.user.SiteUserGetService
import quizleague.web.site.user.SiteUserService
import rxscalajs.{Observable, Subject}
import quizleague.web.model._
import quizleague.web.site.fixtures.{AllFixturesComponent, SimpleFixturesComponent}
import rxscalajs.subjects.ReplaySubject

object ChatModule extends Module {

  override val components = @@(ChatComponent, ChatButton)
}

object ChatMessageService extends ChatMessageGetService with ChatMessagePutService {

  val userService = SiteUserService

}


object ChatService extends ChatGetService {

  val chatMessageService = ChatMessageService

}


