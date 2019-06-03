package quizleague.web

import scalajs.js.JSConverters._
import scalajs.js.Dynamic.literal

package object core {
  object @@{
    def apply[T](items:T*) = items.toJSArray
  }

  val $ = literal

}