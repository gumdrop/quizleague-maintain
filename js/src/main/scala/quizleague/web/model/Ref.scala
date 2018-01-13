package quizleague.web.model

import scalajs.js

class Ref(
  val typeName: String,
  val id: String) extends js.Object

object Ref {
  def apply(
    typeName: String,
    id: String) = new Ref(typeName, id)
}