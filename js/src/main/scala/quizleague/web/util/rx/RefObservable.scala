package quizleague.web.util.rx

import quizleague.domain.Ref
import quizleague.web.model.Key

import scala.scalajs.js
import scala.scalajs.js.annotation._
import quizleague.web.util.Logging._
import rxscalajs.Observable
import rxscalajs.subscription.Subscription
import rxscalajs.facade.ObservableFacade

class RefObservable[+T](val id: String, val obsf: () => Observable[T], val key:Key = null) extends js.Object {

  def obs = obsf()

  def inner = obs.inner

  @JSName("subscribeScala")
  def subscribe(f: T => Unit) = obs.subscribe(f, (x) => Unit, () => Unit)

  def subscribe(onNext: T => Unit, onError: js.Any => Unit, onComplete: () => Unit) = inner.subscribe(onNext, onError, onComplete)

  def toJSON() = js.Dynamic.literal("id" -> id, "key" -> key)

  @inline override def equals(that: Any): scala.Boolean = {
    that match {
      case r: RefObservable[T] => id == r.id
      case _ => false
    }
  }
}

object RefObservable {
  def apply[T](key:Key, obsf: () => Observable[T]):RefObservable[T] = new RefObservable(key.id, obsf,key)
}