package quizleague.web.model

import scalajs.js

trait Model extends js.Object{
  val id:String
}

trait Child {
  var path:String = ""
}