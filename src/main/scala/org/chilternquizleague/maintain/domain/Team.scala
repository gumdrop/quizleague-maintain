package org.chilternquizleague.maintain.domain

case class Team (
    id:String,
    name:String,
    shortName:String,
    venue:Ref[Venue],
    users:List[Ref[User]] = List(),
    retired:Boolean = false) extends Entity