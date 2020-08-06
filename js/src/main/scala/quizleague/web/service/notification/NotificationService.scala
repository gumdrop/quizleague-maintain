package quizleague.web.service.notification

import scalajs.js
import js.JSConverters._
import quizleague.web.service._
import quizleague.web.model._
import quizleague.domain.notification.{ChatMessagePayload => DomCMP, MaintainMessagePayload => DomMMP, Notification => Dom, Payload => DomP, ResultPayload => DomRP}
import shapeless._
import quizleague.web.names.NotificationNames
import io.circe._
import io.circe.parser._
import io.circe.syntax._
import io.circe.scalajs.convertJsToJson
import quizleague.util.json.codecs.DomainCodecs._
import java.time.{LocalDateTime, ZoneOffset, ZonedDateTime}


trait NotificationGetService extends GetService[Notification] with NotificationNames {
  override type U = Dom
  override protected def mapOutSparse(dom: Dom): Notification = new Notification(
      dom.id, 
      dom.typeName, 
      dom.timestamp.toString, 
      mapPayload(dom.payload))
  
  private def mapPayload(payload:DomP) = {
    
    payload match {
      case p: DomRP => new ResultPayload(Key(p.fixtureKey))
      case p: DomMMP => new MaintainMessagePayload(p.message)
      case p: DomCMP => new ChatMessagePayload(Key(p.chatMessageKey), Key(p.siteUserKey))
      case _ => throw new Exception(s"unknown payload type : ${payload.getClass.getName}")
    }
  }
  
  protected def dec(json:js.Any) = decodeJson[U](json)
  
  def messages(typeName:String, threshold:ZonedDateTime) = {
    val q = db.collection(uriRoot).where("timestamp", ">=", threshold.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime.toString.asInstanceOf[js.Any])
    query(q).map(_.filter(_.typeName == typeName)).filter(!_.isEmpty)
  }
}

trait NotificationPutService extends NotificationGetService with PutService[Notification]{
  override def make():U = ???
  override protected def enc(item:U) = item.asJson
  override protected def mapIn(model:Notification):U = ???


}