package quizleague.web.site.chat

import quizleague.web.service.chat.{ChatGetService, ChatPutService}
import quizleague.web.service.user.SiteUserGetService
import quizleague.web.site.user.SiteUserService

object ChatService extends ChatGetService with ChatPutService {

  val userService = SiteUserService

}
