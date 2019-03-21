package quizleague.web.maintain.chat

import quizleague.web.maintain.user.SiteUserService
import quizleague.web.service.chat.{ChatGetService, ChatPutService}


   object ChatService extends ChatGetService with ChatPutService {

    val userService = SiteUserService



}
