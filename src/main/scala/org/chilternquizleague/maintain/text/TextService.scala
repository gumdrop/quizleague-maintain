package org.chilternquizleague.maintain.text

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import org.chilternquizleague.maintain.model.Text
import org.chilternquizleague.maintain.domain.{Text => Dom}
import rxjs.Observable
import shapeless._

@Injectable
@classModeScala
class TextService(override val http:Http) extends EntityService[Text] with TextNames{
  override type U = Dom
  
  val mimeLens = lens[Dom].mimeType
  
  def instance(mimeType:String) = add(mimeLens.set(make())("text/html"))
  
  override protected def mapIn(text:Text) = Dom(text.id, text.text, text.mimeType)
  override protected def mapOut(text:Dom):Observable[Text] = Observable.of(mapOutSparse(text))
  override protected def mapOutSparse(text:Dom):Text = Text(text.id, text.text, text.mimeType)
  
  override protected def make():Dom = Dom(newId(), "", "")
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  override def ser(item:Dom) = item.asJson.noSpaces
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}