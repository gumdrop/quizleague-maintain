package quizleague.web.model

import quizleague.web.util.rx.RefObservable

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

class SiteUser(
                val id: String,
                val handle: String,
                var avatar: String,
                val user: RefObservable[User],
                val uid:Option[String],
                val retired: Boolean = false

              ) extends Model
