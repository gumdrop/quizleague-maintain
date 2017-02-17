package org.chilternquizleague.web.service.globaltext

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.web.model._
import org.chilternquizleague.domain.{GlobalText => Dom}
import org.chilternquizleague.domain.{Ref => DomRef}
import org.chilternquizleague.domain.{Text => DomText}
import rxjs.Observable
import org.chilternquizleague.web.service.text._
import org.chilternquizleague.web.service._
import scalajs.js
import js.JSConverters._
import org.chilternquizleague.web.maintain.globaltext.GlobalTextNames


trait GlobalTextGetService extends GetService[GlobalText] with GlobalTextNames{
  override type U = Dom
  
  val textService:TextGetService
  
  override protected def mapOut(globalText:Dom):Observable[GlobalText] = Observable.of(mapOutSparse(globalText))

  override protected def mapOutSparse(globalText:Dom):GlobalText =
   GlobalText(globalText.id, globalText.name, globalText.text.map {case (k,v) => TextEntry(k, Ref(v.typeName, v.id))}.toJSArray, globalText.retired)
  
  override def flush() = {textService.flush();super.flush()}
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}

trait GlobalTextPutService extends PutService[GlobalText] with GlobalTextGetService{
  
  override val textService:TextPutService
  
  override protected def mapIn(globalText:GlobalText) = Dom(globalText.id, globalText.name, globalText.text.map(_.name).zip(globalText.text.map(te =>DomRef[DomText](te.text.typeName, te.text.id))).toMap, globalText.retired)
  
  override protected def make():Dom = Dom(newId(),"", Map())

  override def save(globalText:GlobalText) = {
    textService.saveAllDirty
    super.save(globalText)
  }
  
  def entryInstance() = {
    val text = textService.getRef(textService.instance())
    TextEntry("", Ref(text.typeName, text.id))
  }
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  override def ser(item:Dom) = item.asJson.noSpaces

}


