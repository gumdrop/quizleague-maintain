package quizleague.domain.notification

import quizleague.domain._
import io.circe.generic.JsonCodec
import java.time.LocalDateTime


object NotificationTypeNames{
  
  val result = "result"
  val maintain = "maintain"
  val chat = "chat"
  
}


case class Notification(
  id:String,
  typeName:String,
  timestamp:LocalDateTime,
  payload:Payload,
  retired:Boolean = false
  
) extends Entity

@JsonCodec
sealed trait Payload

case class ResultPayload(fixtureKey:String) extends Payload
case class MaintainMessagePayload(message:String) extends Payload
case class ChatMessagePayload(chatMessageKey:String, siteUserKey:String) extends Payload