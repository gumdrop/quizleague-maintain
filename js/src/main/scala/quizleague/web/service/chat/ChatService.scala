package quizleague.web.service.chat

import java.time.LocalDateTime

import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{Chat => Dom, ChatMessage => DomMessage, Key => DomKey}
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
  val chatMessageService: ChatMessageGetService

  override protected def mapOutSparse(chat: Dom) = new Chat(
    chat.id,
    chat.name.getOrElse(null),
    chatMessageService.list(Key(chat.key)),
    chat.retired
    )

  protected def dec(json:js.Any) = decodeJson[U](json)


}

trait ChatPutService extends PutService[Chat] with ChatGetService {

  override protected def mapIn(chat: Chat) = Dom(
    chat.id,
    Option(chat.name),
    chat.retired)

  override protected def make() = Dom(id = newId())

  def instance(parentKey:Key, name:String) = {
    val id = newId()
    mapOutWithKey(make().copy(name = Some(name)).withKey(DomKey(DomKey(parentKey.key),uriRoot,id)))
  }

  override def enc(item: Dom) = item.asJson

}