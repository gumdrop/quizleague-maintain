package quizleague.web.maintain.component

import scalajs.js
import quizleague.web.util.rx.RefObservable
import quizleague.web.service.GetService
import rxscalajs.Observable._
import rxscalajs.Observable
import quizleague.web.model.Model
import js.JSConverters._

object SelectUtils {
  def model[T <: Model](service:GetService[T])(nameMaker: T => String):Observable[js.Array[SelectWrapper[T]]] = service.list.map(_.map(o => new SelectWrapper(nameMaker(o),service.refObs(o.id)))).map(_.sortBy(_.text))
  def model[T <: Model](items:js.Array[RefObservable[T]], service:GetService[T])(nameMaker: T => String)(filter: T => Boolean = (t:T)=>true):Observable[js.Array[SelectWrapper[T]]] = {
    combineLatest(
        items.map(_.obs))
        .map(_.filter(filter).map(o => new SelectWrapper(nameMaker(o),service.refObs(o.id)))).map(_.sortBy(_.text).toJSArray)
  }

}

class SelectWrapper[T](val text:String, val value:RefObservable[T]) extends js.Object