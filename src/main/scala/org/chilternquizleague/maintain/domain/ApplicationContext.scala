package org.chilternquizleague.maintain.domain

case class ApplicationContext (
  id:String,
  leagueName:String,
  textSet:Ref[GlobalText],
  //currentSeason:Season,
  senderEmail:String,
  emailAliases:List[EmailAlias],
  retired:Boolean = false  
) extends Entity

case class EmailAlias(alias:String, user:Ref[User])