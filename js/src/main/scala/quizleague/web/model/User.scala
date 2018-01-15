package quizleague.web.model

import scala.scalajs.js


class User(
    val id:String,
    val name:String,
    val email:String,
    val retired:Boolean = false
) extends Model
object User{
  def apply(id:String,
    name:String,
    email:String,
    retired:Boolean = false) = new User(id,name,email,retired)
}
