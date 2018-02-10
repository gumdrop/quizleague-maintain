package quizleague.web.model


import scalajs.js


class GlobalText(val id:String, val name:String, val text:js.Array[TextEntry], val retired:Boolean=false) extends Model

object GlobalText{
  def apply(id:String, name:String, text:js.Array[TextEntry], retired:Boolean=false) = new GlobalText(id,name,text,retired)
}


class TextEntry(val name:String, val text:Ref) extends js.Object

object TextEntry{
  def apply(name:String, text:Ref) = new TextEntry(name, text)
}