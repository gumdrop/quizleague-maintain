package quizleague.web.maintain.text

import quizleague.web.maintain.component.ItemComponent
import quizleague.domain.Entity
import quizleague.web.model.Text
import angulate2.router.Router
import scala.scalajs.js.annotation.JSExport

trait TextEditMixin[T] {
  this: ItemComponent[T] =>

  val router: Router

  @JSExport
  def editText(text: Text) = {
    service.cache(item)
    router.navigateTo("/text", text.id)
  }
}