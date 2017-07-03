package quizleague.web.util.rx

import quizleague.domain.Ref
import rxjs.Observable
import rxjs.core.Subscription
import scala.scalajs.js
import scala.scalajs.js.annotation._
import quizleague.web.util.Logging._

@ScalaJSDefined
class RefObservable[+T](val id: String, val obs: Observable[T]) extends js.Object {

  @JSName("subscribeScala")
  def subscribe[R](f: T => R) = obs.subscribe(f)

  @JSName("subscribe")
  def subscribeJS(
    next: js.UndefOr[js.Function1[T, _]],
    error: js.UndefOr[js.Function1[js.Dynamic, _]],
    complete: js.UndefOr[js.Function0[_]]): Subscription = {
    
    obs.subscribeJS(next, error, complete)
  }
  
  def toJSON() = js.Dynamic.literal(("id",id))

}

object RefObservable {

  def apply[T](ref: Ref[_], obs: Observable[T]) = new RefObservable(ref.id, obs)
  def apply[T](id: String, obs: Observable[T]) = new RefObservable(id, obs)
}