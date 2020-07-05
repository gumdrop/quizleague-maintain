package quizleague.web.service.notification

import scalajs.js
import js.JSConverters._
import quizleague.web.service._
import quizleague.web.model._
import quizleague.domain.notification.{ Notification => Dom, ResultPayload => DomRP, MaintainMessagePayload => DomMMP, Payload => DomP }
import shapeless._
import quizleague.web.names.NotificationNames
import io.circe._,io.circe.parser._,io.circe.syntax._,io.circe.scalajs.convertJsToJson
import quizleague.util.json.codecs.DomainCodecs._
import java.time.LocalDateTime


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
      case _ => throw new Exception(s"unknown payload type : ${payload.getClass.getName}")
    }
  }
  
  protected def dec(json:js.Any) = decodeJson[U](json)
  
  def messages(typeName:String, threshold:LocalDateTime) = {
    val q = db.collection(uriRoot).where("timestamp", ">=", threshold.toString.asInstanceOf[js.Any])
    query(q).map(_.filter(_.typeName == typeName)).filter(!_.isEmpty)
  }
}