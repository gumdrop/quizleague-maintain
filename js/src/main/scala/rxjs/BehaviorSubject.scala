package rxjs

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("rxjs/BehaviorSubject","BehaviorSubject")
class BehaviorSubject[T](initial:T) extends Subject[T]{
  def this() = this(js.undefined.asInstanceOf[T])
}

@js.native
@JSImport("rxjs/ReplaySubject","ReplaySubject")
class ReplaySubject[T](buffer:Int) extends Subject[T]