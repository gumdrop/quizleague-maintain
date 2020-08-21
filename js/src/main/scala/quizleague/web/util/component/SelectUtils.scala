package quizleague.web.util.component

import quizleague.web.model.Model
import quizleague.web.service.GetService
import quizleague.web.util.rx.RefObservable
import rxscalajs.Observable
import rxscalajs.Observable._

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import quizleague.web.util.Logging._

object SelectUtils {
  def model[T <: Model](service:GetService[T])(nameMaker: T => String):Observable[js.Array[SelectWrapper[T]]] = service.list().map(_.map(o => new SelectWrapper(nameMaker(o),service.refObs(o.key)))).map(_.sortBy(_.text))
  def model[T <: Model](items:Observable[js.Array[T]], service:GetService[T])(nameMaker: T => String):Observable[js.Array[SelectWrapper[T]]] = items.map(_.map(o => new SelectWrapper(nameMaker(o),service.refObs(o.key)))).map(_.sortBy(_.text))
  def model[T <: Model](items:js.Array[RefObservable[T]], service:GetService[T])(nameMaker: T => String)(filter: T => Boolean = (t:T)=>true):Observable[js.Array[SelectWrapper[T]]] = {
    combineLatest(
        items.map(_.obs))
        .map(_.filter(filter).map(o => new SelectWrapper(nameMaker(o),service.refObs(o.key)))).map(_.sortBy(_.text).toJSArray)
  }
  def objectModel[T <: Model](items:Observable[js.Array[T]])(nameMaker: T => String)(filter: T => Boolean = (t:T)=>true):Observable[js.Array[SelectObjectWrapper[T]]] = items.map(_.filter(filter).map(o => new SelectObjectWrapper(nameMaker(o),o))).map(_.sortBy(_.text))

}

class SelectWrapper[T](val text:String, val value:RefObservable[T]) extends js.Object
class SelectObjectWrapper[T](val text:String, val value:T) extends js.Object
