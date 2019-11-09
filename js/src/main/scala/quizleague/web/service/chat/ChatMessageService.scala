package quizleague.web.service.chat

import java.time.LocalDateTime

import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{ChatMessage => Dom, Key => DomKey}
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames

import scala.scalajs.js.JSConverters._
import quizleague.web.service._
import quizleague.web.service.venue._
import quizleague.web.service.text._
import quizleague.web.service.user._
import quizleague.web.names._
import io.circe.parser._
import io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.web.util.Logging
import rxscalajs.Observable

import scalajs.js

trait ChatMessageGetService extends GetService[ChatMessage] with ChatMessageNames {

  override type U = Dom

  val userService: SiteUserGetService

  override protected def mapOutSparse(message: Dom) = new ChatMessage(
    message.id,
    userService.refObs(message.user.id), message.message, message.date.toString)

  protected def dec(json:js.Any) = decodeJson[U](json)

}

trait ChatMessagePutService extends PutService[ChatMessage] with ChatMessageGetService {

  val chatService:ChatGetService

  override protected def mapIn(message: ChatMessage) = Dom(
    message.id,
    userService.ref(message.user), message.message, LocalDateTime.parse(message.date)
    )

  override protected def make() = Dom(newId(), null,"", LocalDateTime.now())

  override def enc(item: Dom) = item.asJson

  def saveMessage(text:String, siteUserID:String, chatKey:Key) = {
    val msg = make(DomKey(chatKey.key))
    save(msg.copy(user=userService.ref(siteUserID), message=text).withKey(msg.key.get))
  }

}