package quizleague.web.model

import angulate2.std.Data
import scalajs.js

@Data
case class GlobalText(id:String, name:String, text:js.Array[TextEntry], retired:Boolean=false)

@Data
case class TextEntry(name:String, text:Ref)