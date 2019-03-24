package quizleague.web.maintain.chat

import quizleague.web.maintain.user.SiteUserService
import quizleague.web.service.chat.{ChatGetService, ChatMessageGetService, ChatMessagePutService, ChatPutService}


object ChatMessageService extends ChatMessageGetService with ChatMessagePutService{
  val userService = SiteUserService
}

object ChatService extends ChatGetService with ChatPutService {
  val chatMessageService = ChatMessageService

}
