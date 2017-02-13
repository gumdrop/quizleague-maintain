package org.chilternquizleague.web.model

import angulate2.std.Data
import scalajs.js

@Data
case class ApplicationContext (
  id:String,
  leagueName:String,
  textSet:GlobalText,
  //currentSeason:Season,
  senderEmail:String,
  emailAliases:js.Array[EmailAlias]
)

@Data
case class EmailAlias(alias:String, user:User)