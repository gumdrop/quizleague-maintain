package quizleague.web.model

import quizleague.web.util.rx.RefObservable
import scalajs.js
import scala.scalajs.js.annotation._

class Team (
    val id:String,
    val name:String,
    val shortName:String,
    val venue:RefObservable[Venue],
    val text:RefObservable[Text],
    val users:js.Array[RefObservable[User]],
    val retired:Boolean
)  extends Model

object Team{
  
  def apply(   id:String,
    name:String,
    shortName:String,
    venue:RefObservable[Venue],
    text:RefObservable[Text],
    users:js.Array[RefObservable[User]],
    retired:Boolean) = new Team(
      id,name,shortName,venue,text,users,retired    
    )
  
  
}