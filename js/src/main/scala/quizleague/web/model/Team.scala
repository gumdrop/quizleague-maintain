package quizleague.web.model

import angulate2.std.Data
import scala.scalajs.js
import rxjs.Observable
import quizleague.web.util.rx.RefObservable

@Data
case class Team(
    id:String,
    name:String,
    shortName:String,
    venue:RefObservable[Venue],
    text:RefObservable[Text],
    users:js.Array[RefObservable[User]],
    retired:Boolean
)