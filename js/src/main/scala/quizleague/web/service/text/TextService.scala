package quizleague.web.service.text

import scalajs.js
import quizleague.web.service._
import quizleague.web.model.Text
import quizleague.domain.{ Text => Dom }
import shapeless._
import quizleague.web.names.TextNames
import io.circe._,io.circe.parser._,io.circe.syntax._,io.circe.scalajs.convertJsToJson
import quizleague.util.json.codecs.DomainCodecs._


trait TextGetService extends GetService[Text] with TextNames {
  override type U = Dom
  override protected def mapOutSparse(text: Dom): Text = Text(text.id, text.text, text.mimeType)
  protected def dec(json:js.Any) = decodeJson[U](json)
}

trait TextPutService extends PutService[Text] with TextGetService with DirtyListService[Text]{

  val mimeLens = lens[Dom].mimeType

  def instance(mimeType: String = "text/html") = add(mimeLens.set(make())(mimeType))

  override protected def mapIn(text: Text) = Dom(text.id, text.text, text.mimeType)

  override protected def make(): Dom = Dom(newId(), "", "text/html")

  override def enc(item: Dom) = item.asJson

}