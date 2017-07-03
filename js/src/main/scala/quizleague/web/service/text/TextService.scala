package quizleague.web.service.text

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service._
import quizleague.web.model.Text
import quizleague.domain.{ Text => Dom }
import rxjs.Observable
import shapeless._
import quizleague.web.names.TextNames


trait TextGetService extends GetService[Text] with TextNames {
    import io.circe._, io.circe.generic.auto._, io.circe.parser._
  override type U = Dom
  override protected def mapOutSparse(text: Dom): Text = Text(text.id, text.text, text.mimeType)
  protected def dec(json:String) = decode[U](json)
  protected def decList(json:String) = decode[List[U]](json)
}

trait TextPutService extends PutService[Text] with TextGetService with DirtyListService[Text]{

  val mimeLens = lens[Dom].mimeType

  def instance(mimeType: String) = add(mimeLens.set(make())("text/html"))

  override protected def mapIn(text: Text) = Dom(text.id, text.text, text.mimeType)

  override protected def make(): Dom = Dom(newId(), "", "")

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  override def ser(item: Dom) = item.asJson.noSpaces

}