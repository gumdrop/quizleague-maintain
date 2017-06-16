package quizleague.web.model

import angulate2.std.Data
import scala.scalajs.js
import rxjs.Observable

@Data
case class Team(
    id:String,
    name:String,
    shortName:String,
    venue:Observable[Venue],
    text:Observable[Text],
    users:Observable[js.Array[User]],
    retired:Boolean
)