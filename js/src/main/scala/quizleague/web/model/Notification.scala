package quizleague.web.model

import scala.scalajs.js

class Notification(
  val id:String,
  val typeName:String,
  val timestamp:String,
  val payload:Payload
  
) extends Model


sealed trait Payload

case class ResultPayload(val fixtureKey:Key) extends Payload
case class MaintainMessagePayload(val message:String) extends Payload
case class ChatMessagePayload(val chatMessageKey:Key, siteUserKey:Key) extends Payload