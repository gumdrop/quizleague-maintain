package quizleague.web.service.globaltext

import quizleague.web.model._
import quizleague.domain.{GlobalText => Dom}
import quizleague.domain.{Ref => DomRef}
import quizleague.domain.{Text => DomText}
import rxscalajs.Observable
import quizleague.web.service.text._
import quizleague.web.service._
import scalajs.js
import js.JSConverters._
import quizleague.web.names.GlobalTextNames
import io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._


trait GlobalTextGetService extends GetService[GlobalText] with GlobalTextNames{
  override type U = Dom


  
  val textService:TextGetService
  
  override protected def mapOutSparse(globalText:Dom):GlobalText =
   GlobalText(globalText.id, globalText.name, globalText.text.map {case (k,v) => TextEntry(k, Ref(v.typeName, v.id))}.toJSArray, globalText.retired)
  
  override def flush() = {textService.flush();super.flush()}
  
  override protected def dec(json:js.Any) = decodeJson[U](json)

}

trait GlobalTextPutService extends PutService[GlobalText] with GlobalTextGetService{
  
  override val textService:TextPutService
  
  override protected def mapIn(globalText:GlobalText) = Dom(globalText.id, globalText.name, globalText.text.map(_.name).zip(globalText.text.map(te =>DomRef[DomText](te.text.typeName, te.text.id))).toMap, globalText.retired)
  
  override protected def make():Dom = withKey(Dom(newId(),"", Map()), null)

  override def save(globalText:GlobalText) = {
    textService.saveAllDirty
    super.save(globalText)
  }
  
  def entryInstance() = {
    val text = textService.getRef(textService.instance())
    TextEntry("", Ref(text.typeName, text.id))
  }
  
  override def enc(item: Dom) = item.asJson

}



