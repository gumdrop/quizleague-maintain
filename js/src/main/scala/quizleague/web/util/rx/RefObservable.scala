package quizleague.web.util.rx

import rxjs.Observable
import quizleague.domain.Ref
import scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
class RefObservable[T](val id:String, val obs:Observable[T]) extends js.Object{
  def subscribe(f:T => Any) = obs.subscribe(f)

  def canEqual(other: Any) = {
    other.isInstanceOf[quizleague.web.util.rx.RefObservable[T]]
  }

  override def equals(other: Any) = {
    other match {
      case that: quizleague.web.util.rx.RefObservable[T] => that.canEqual(RefObservable.this) && id == that.id
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime + id.hashCode
  }
}

object RefObservable {
  
  def apply[T](ref:Ref[_], obs:Observable[T]) = new RefObservable(ref.id,obs)
  def apply[T](id:String, obs:Observable[T]) = new RefObservable(id,obs)
}