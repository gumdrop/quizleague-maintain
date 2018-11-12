package quizleague.web.model

import quizleague.web.util.rx.RefObservable
import rxscalajs.Observable

import scala.scalajs.js


class User(
    val id:String,
    val name:String,
    val email:String,
    val teams:Observable[js.Array[TeamTenure]],
    val retired:Boolean = false
) extends Model
object User{
  def apply(id:String,
    name:String,
    email:String,
    retired:Boolean = false) = new User(id,name,email,Observable.just(js.Array()),retired)
}

class TeamTenure(
                val id:String,
                val team:RefObservable[Team],
                val start:String
                )
