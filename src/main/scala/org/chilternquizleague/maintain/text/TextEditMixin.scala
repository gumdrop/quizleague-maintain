package org.chilternquizleague.maintain.text

import org.chilternquizleague.maintain.component.ItemComponent
import org.chilternquizleague.domain.Entity
import org.chilternquizleague.web.model.Text
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