package quizleague.web.site.chat

import quizleague.util.collection._
import quizleague.web.core.{@@, Module}
import quizleague.web.model._
import quizleague.web.service.chat.{ChatGetService, ChatMessageGetService, ChatMessagePutService, ChatPutService}
import quizleague.web.site.user.SiteUserService
import rxscalajs.Observable

import scala.scalajs.js

object ChatModule extends Module {

  override val components = @@(ChatComponent, LoginButton, HotChats)
}

object ChatMessageService extends ChatMessageGetService with ChatMessagePutService {

  val userService = SiteUserService
  val chatService = ChatService

  def listMessages(chatKey:Key):Observable[js.Array[ChatMessage]] =
    list(chatKey).map(_.sortBy(_.date)(Desc))

  def hotChats() = {
    query(db.collectionGroup(typeName).orderBy("date","desc").limit(5))
  }

}


object ChatService extends ChatGetService with ChatPutService {

  val chatMessageService = ChatMessageService

  def add(parentKey:Key, name:String):Observable[Key] = {
    val chat = instance(parentKey,name)

    save(chat).map(x => chat.key)

  }
}


