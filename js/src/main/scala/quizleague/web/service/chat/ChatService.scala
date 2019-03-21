package quizleague.web.service.chat

import java.time.LocalDateTime

import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{Chat => Dom, ChatMessage => DomMessage}
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames

import scala.scalajs.js.JSConverters._
import quizleague.web.service._
import quizleague.web.service.venue._
import quizleague.web.service.text._
import quizleague.web.service.user._
import quizleague.web.names.ChatNames
import io.circe.parser._
import io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._

import scalajs.js

trait ChatGetService extends GetService[Chat] with ChatNames {

  override type U = Dom

  val userService: SiteUserGetService

  override protected def mapOutSparse(chat: Dom) = new Chat(
    chat.id,
    chat.messages.map(c => new ChatMessage(userService.refObs(c.user.id), c.message, c.date.toString)).toJSArray,
    chat.retired
    )

  protected def dec(json:js.Any) = decodeJson[U](json)


}

trait ChatPutService extends PutService[Chat] with ChatGetService {

  override protected def mapIn(chat: Chat) = Dom(
    chat.id,
    chat.messages.map(c => DomMessage(userService.ref(c.user), c.message, LocalDateTime.parse(c.date))).toList,
    chat.retired)

  override protected def make() = Dom(newId(), List(), false)

  override def enc(item: Dom) = item.asJson

}