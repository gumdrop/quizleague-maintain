package quizleague.web.service


import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import scala.scalajs.js.annotation.ScalaJSDefined
import quizleague.web.names.ComponentNames
import quizleague.web.model.Model

trait EntityService[T <: Model] extends GetService[T] with PutService[T]{
    this:ComponentNames =>
}
