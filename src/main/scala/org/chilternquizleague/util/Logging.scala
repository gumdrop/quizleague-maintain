package org.chilternquizleague.util

import scalajs.js
import js.Dynamic.{ global => g }

trait Logging {
  def log[A](i:A, message:String="", serialise:Boolean=true):A = {
    g.console.log(message + (if(serialise) js.JSON.stringify(i.asInstanceOf[js.Any]) else i))
    i
  }
}