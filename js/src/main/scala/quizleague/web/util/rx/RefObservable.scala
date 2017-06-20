package quizleague.web.util.rx

import rxjs.Observable
import quizleague.domain.Ref
import scala.scalajs.js.annotation.JSExport

@JSExport
class RefObservable[T](val ref:Ref[_], val obs:Observable[T]) {
  def id = ref.id
  
  def subscribe(f:T => Any) = obs.subscribe(f)
}

object RefObservable {
  
  def apply[T](ref:Ref[_], obs:Observable[T]) = new RefObservable(ref,obs)
}