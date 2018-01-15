package quizleague.web

import scalajs.js.JSConverters._

package object core {
  object @@{
    def apply[T](items:T*) = items.toJSArray
  }
  
 
}