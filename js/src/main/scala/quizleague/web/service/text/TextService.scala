package quizleague.web.service.text

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service._
import quizleague.web.model.Text
import quizleague.domain.{ Text => Dom }
import rxjs.Observable
import shapeless._
import quizleague.web.maintain.text.TextNames

trait TextGetService extends GetService[Text] with TextNames {
  override type U = Dom
  override protected def mapOut(text: Dom): Observable[Text] = Observable.of(mapOutSparse(text))
  override protected def mapOutSparse(text: Dom): Text = Text(text.id, text.text, text.mimeType)
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]
}

trait TextPutService extends PutService[Text] with TextGetService with DirtyListService[Text]{

  val mimeLens = lens[Dom].mimeType

  def instance(mimeType: String) = add(mimeLens.set(make())("text/html"))

  override protected def mapIn(text: Text) = Dom(text.id, text.text, text.mimeType)

  override protected def make(): Dom = Dom(newId(), "", "")

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  override def ser(item: Dom) = item.asJson.noSpaces

}