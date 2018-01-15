package quizleague.web.model

import scala.scalajs.js


class Text(
    val id:String,
    val text:String,
    val mimeType:String
) extends Model

object Text{
  def apply(id:String,
    text:String,
    mimeType:String) = new Text(id,text,mimeType)
}