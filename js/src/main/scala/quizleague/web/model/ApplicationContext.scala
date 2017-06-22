package quizleague.web.model

import angulate2.std.Data
import scalajs.js
import quizleague.web.util.rx.RefObservable

@Data
case class ApplicationContext (
  id:String,
  leagueName:String,
  textSet:RefObservable[GlobalText],
  currentSeason:RefObservable[Season],
  senderEmail:String,
  emailAliases:js.Array[EmailAlias]
)

@Data
case class EmailAlias(alias:String, user:RefObservable[User])