package rxjs

import scala.scalajs.js

@js.native
trait Subscribable[T] extends js.Any{
  def asObservable(): Observable[T] = js.native
  def isActive(mq:String):Boolean = js.native
}