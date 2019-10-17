package quizleague.web.model

import scalajs.js

abstract class Model extends js.Object {
  val id:String
  var parentKey:String = null
}
